package product.microservice.productmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.ProductType;
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
}
