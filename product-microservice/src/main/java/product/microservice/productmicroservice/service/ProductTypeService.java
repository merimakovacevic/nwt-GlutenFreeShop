package product.microservice.productmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.grpc.GRPCClientService;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ProductTypeRepository;

import java.util.Optional;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private GRPCClientService grpcClientService;

    public Iterable<ProductType> getAll() {
        return productTypeRepository.findAll();
    }

    public ProductType getById(Long id){
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if(productType.isEmpty()) throw new ApiRequestException("Product type with id "+ id + " does not exist");
        grpcClientService.sendSystemEvent("PRODUCT_TYPE_GET_BY_ID", "GET");
        return productType.get();
    }

    public String addNew(ProductType productType){
        if (productType.getName().equals("") || productType.getName() == null) throw new ApiRequestException("Name is not valid");
        productTypeRepository.save(productType);
        grpcClientService.sendSystemEvent("PRODUCT_TYPE_CREATE", "POST");
        return "Saved";
    }

    public String deleteProductTypeById(Long id){
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if (productType.isEmpty()) throw new ApiRequestException("Product type with id " + id + " does not exist!");
        productTypeRepository.deleteById(id);
        grpcClientService.sendSystemEvent("PRODUCT_TYPE_DELETE", "DELETE");
        return "Deleted";
    }

    public String updateProductType(ProductType newProductType, Long id){
        Long productTypeId = newProductType.getId();
        if (productTypeId == null) throw new ApiRequestException("Id in object is not valid!");
        if (productTypeId != id) throw new ApiRequestException("Id in url is not equal id in object!");
        if (newProductType.getName() == "" || newProductType.getName() == null) throw new ApiRequestException("Name is not valid!");
        ProductType productType = productTypeRepository.findById(id).get();
        productType.setName(newProductType.getName());
        productTypeRepository.save(productType);
        grpcClientService.sendSystemEvent("PRODUCT_TYPE_UPDATE_BY_ID", "UPDATE");
        return "Updated";
    }
}
