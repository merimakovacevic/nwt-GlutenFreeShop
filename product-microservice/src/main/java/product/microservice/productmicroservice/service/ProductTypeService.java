package product.microservice.productmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.model.Rating;
import product.microservice.productmicroservice.repository.ProductTypeRepository;

import java.util.Optional;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    public Iterable<ProductType> getAll() {
        return productTypeRepository.findAll();
    }

    public ProductType getById(Integer id){
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if(productType.isEmpty()) throw new ApiRequestException("Product type with id "+ id + " does not exist");
        return productType.get();
    }

    public String addNew(ProductType productType){
        if (productType.getName().equals("") || productType.getName() == null) throw new ApiRequestException("Name is not valid");
        productTypeRepository.save(productType);
        return "Saved";
    }

    public String deleteProductTypeById(Integer id){
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if (productType.isEmpty()) throw new ApiRequestException("Product type with id " + id + " does not exist!");
        productTypeRepository.deleteById(id);
        return "Deleted";
    }

    public String updateProductType(ProductType newProductType, Integer id){
        Integer productTypeId = newProductType.getId();
        if (productTypeId == null) throw new ApiRequestException("Id in object is not valid!");
        if (productTypeId != id) throw new ApiRequestException("Id in url is not equal id in object!");
        if (newProductType.getName() == "" || newProductType.getName() == null) throw new ApiRequestException("Name is not valid!");
        ProductType productType = productTypeRepository.findById(id).get();
        productType.setName(newProductType.getName());
        productTypeRepository.save(productType);
        return "Updated";
    }
}
