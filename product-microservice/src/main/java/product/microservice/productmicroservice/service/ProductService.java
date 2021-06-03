package product.microservice.productmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import product.microservice.productmicroservice.amqp.event.OrderCreatedEvent;
import product.microservice.productmicroservice.amqp.event.OrderItemInfo;
import product.microservice.productmicroservice.controller.dto.CalculatePriceDTO;
import org.springframework.web.bind.MethodArgumentNotValidException;
import product.microservice.productmicroservice.dto.mapper.Mapper;
import product.microservice.productmicroservice.dto.model.ProductDto;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.exception.EntityType;
import product.microservice.productmicroservice.exception.RestResponseException;
import product.microservice.productmicroservice.model.Image;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ImageRepository;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.ProductTypeRepository;

import java.net.URL;
import java.util.Collection;
import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;

    public List<ProductDto> getAll() {
        return productRepository.findAll().stream().map(Mapper::toProductDto).collect(Collectors.toList());
    }

    public ProductDto addNewProduct(ProductDto productDto) {
        Optional<ProductType> productType = productTypeRepository.findProductTypeByName(productDto.getProductTypeName());
        if (productType.isEmpty()) {
            throw new RestResponseException(HttpStatus.BAD_REQUEST, EntityType.PRODUCT_TYPE);
        }

        Product product = productRepository.save(new Product(productDto.getName(), productDto.getDescription(),
                                                productDto.getStock(), productDto.getPrice(), productType.get()));

//        List<String> urls = productDto.getUrls();
//        Set<Image> images = urls.stream().map(url -> {
//            Optional<Image> image = imageRepository.findImageByUrl(url);
//            if (image.isPresent()) {
//                throw new RestResponseException(HttpStatus.CONFLICT, EntityType.IMAGE);
//            }
//            else return imageRepository.save(new Image(url, product));
//        }).collect(Collectors.toSet());
//        product.setImages(images);
        productRepository.save(product);

        return Mapper.toProductDto(product);
    }

    public ProductDto getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.PRODUCT);
        }
        return Mapper.toProductDto(product.get());
    }

    public void deleteProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.PRODUCT);
        }
        productRepository.deleteById(id);
    }

    public ProductDto updateProduct(Product newProduct, Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.PRODUCT);
        }
        Long typeId = newProduct.getProductType().getId();
        if (typeId == null) throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.PRODUCT_TYPE);
        Optional<ProductType> productType = productTypeRepository.findById(typeId);
        if (productType.isEmpty()) {
            throw new RestResponseException(HttpStatus.BAD_REQUEST, EntityType.PRODUCT_TYPE);
        }

//        imageRepository.deleteAllByProductId(productId);
//        List<String> urls = productDto.getUrls();
//        Set<Image> images = urls.stream().map(url -> {
//            Optional<Image> image = imageRepository.findImageByUrl(url);
//            if (image.isPresent()) {
//                throw new RestResponseException(HttpStatus.CONFLICT, EntityType.IMAGE);
//            }
//            return imageRepository.save(new Image(url, product.get()));
//        }).collect(Collectors.toSet());

        Product productToUpdate = productRepository.findById(productId).get();
        productToUpdate.setName(newProduct.getName());
        productToUpdate.setDescription(newProduct.getDescription());
        productToUpdate.setStock(newProduct.getStock());
        productToUpdate.setPrice(newProduct.getPrice());
        productRepository.save(productToUpdate);

        return Mapper.toProductDto(productToUpdate);
    }

    public List<ProductDto> findProductsByProductTypeName(String name) {
        Optional<ProductType> productType = productTypeRepository.findProductTypeByName(name);
        if (productType.isEmpty()) {
            throw new RestResponseException(HttpStatus.BAD_REQUEST, EntityType.PRODUCT_TYPE);
        }
        List<Product> products = productRepository.findAllByProductTypeName(name);
        return products.stream().map(Mapper::toProductDto).collect(Collectors.toList());
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
            }
        }

    }

    public Product getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new ApiRequestException("Product with id " + id + " does not exist");
        return product.get();
    }
}
