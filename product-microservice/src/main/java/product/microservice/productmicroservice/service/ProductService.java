package product.microservice.productmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product.microservice.productmicroservice.amqp.event.OrderCreatedEvent;
import product.microservice.productmicroservice.amqp.event.OrderItemInfo;
import product.microservice.productmicroservice.controller.dto.CalculatePriceDTO;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.grpc.GRPCClientService;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.ProductTypeRepository;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private GRPCClientService grpcClientService;

    public Iterable<Product> getAll() {
        Iterable<Product> products = productRepository.findAll();
        grpcClientService.sendSystemEvent("PRODUCT_GET_ALL", "GET");
        return products;
    }

    public String addNew(Product product) {
        if (product.getProductType() == null) throw new ApiRequestException("Product type not assigned");
        Long typeId = product.getProductType().getId();
        if (typeId == null) throw new ApiRequestException("Product type not assigned");
        Optional<ProductType> productType = productTypeRepository.findById(typeId);
        if (productType.isEmpty()) throw new ApiRequestException("Product type with id " + typeId + " does not exist!");
        if (product.getName().equals("") || product.getName() == null)
            throw new ApiRequestException("Name is not valid");
        if (product.getDescription().equals("") || product.getDescription() == null)
            throw new ApiRequestException("Description is not valid");
        product.setProductType(productType.get());
        Product savedProduct = productRepository.save(product);
        grpcClientService.sendSystemEvent("PRODUCT_CREATE", "POST");
        return "Saved";
    }

    public Product getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new ApiRequestException("Product with id " + id + " does not exist");
        grpcClientService.sendSystemEvent("PRODUCT_GET_BY_ID", "GET");
        return product.get();
    }

    public String deleteProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new ApiRequestException("Product with id " + id + " does not exist");
        productRepository.deleteById(id);
        grpcClientService.sendSystemEvent("PRODUCT_DELETE_BY_ID", "DELETE");
        return "Deleted";
    }

    public String updateProduct(Product newProduct, Long id) {
        Long productId = newProduct.getId();
        if (productId == null) throw new ApiRequestException("Id in object is not valid!");
        if (productId != id) throw new ApiRequestException("Id in url is not equal id in object!");
        if (newProduct.getName() == "" || newProduct.getName() == null)
            throw new ApiRequestException("Name is not valid!");
        Product product = productRepository.findById(id).get();
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        productRepository.save(product);
        grpcClientService.sendSystemEvent("PRODUCT_UPDATE_BY_ID", "UPDATE");
        return "Updated";
    }

    public List<Product> findProductsByName(String name) {
        List<Product> products = productRepository.findByName(name);
        if (products.isEmpty()) throw new ApiRequestException("There are no products with name " + name);
        grpcClientService.sendSystemEvent("PRODUCT_FIND_BY_NAME", "GET");
        return products;
    }

    public Iterable<Product> findProductsByProductType(Long id) {
        List<Product> products = productRepository.findByProductTypeId(id);
        if (products.isEmpty()) throw new ApiRequestException("Product with id " + id + " does not exist");
        grpcClientService.sendSystemEvent("PRODUCT_FIND_BY_TYPE", "GET");
        return products;
    }

    public Iterable<Product> findProductsByProductTypeName(String name) {
        List<Product> types = productRepository.findByProductTypeName(name);
        if (types.isEmpty()) throw new ApiRequestException("There are no product types with name " + name);
        grpcClientService.sendSystemEvent("PRODUCT_FIND_BY_TYPE_NAME", "GET");
        return types;
    }

    public Set<Long> updateStockForItemList(List<OrderItemInfo> itemInfoList) {
        Set<Long> outOfStockProductIds = new LinkedHashSet<>();

        for (OrderItemInfo orderItemInfo : itemInfoList) {
            Optional<Product> optProduct = productRepository.findById(orderItemInfo.getProductId());

            if (optProduct.isEmpty() || optProduct.get().getStock() < orderItemInfo.getQuantity()) {
                outOfStockProductIds.add(orderItemInfo.getProductId());
            }
        }

        if (outOfStockProductIds.isEmpty()) {
            for (OrderItemInfo orderItemInfo : itemInfoList) {
                Product product = getById(orderItemInfo.getProductId());

                product.setStock(product.getStock() - orderItemInfo.getQuantity());
                productRepository.save(product);
            }

            return outOfStockProductIds;
        }

        return outOfStockProductIds;
    }

    public Double calculatePrice(List<OrderItemInfo> itemInfoList) {
        double amount = 0.0;

        for (OrderItemInfo orderItemInfo : itemInfoList) {
            Optional<Product> optionalProduct = productRepository.findById(orderItemInfo.getProductId());

            if (optionalProduct.isEmpty()) {
                return 0.0;
            }

            amount += optionalProduct.get().getPrice() * orderItemInfo.getQuantity();
        }

        return amount;
    }

    public void returnStock(List<OrderItemInfo> itemInfoList) {
        for (OrderItemInfo orderItemInfo : itemInfoList) {
            Optional<Product> optionalProduct = productRepository.findById(orderItemInfo.getProductId());

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setStock(product.getStock() + orderItemInfo.getQuantity());
                productRepository.save(product);
            }
        }

    }
}
