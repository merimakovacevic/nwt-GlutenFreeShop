package product.microservice.productmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import product.microservice.productmicroservice.dto.mapper.Mapper;
import product.microservice.productmicroservice.dto.model.ProductTypeDto;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.exception.EntityType;
import product.microservice.productmicroservice.exception.RestResponseException;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ProductTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    public List<ProductTypeDto> getAll() {
        return productTypeRepository.findAll().stream().map(Mapper::toProductTypeDto).collect(Collectors.toList());
    }

    public ProductTypeDto getProductTypeById(Long id){
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if(productType.isEmpty()) throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.PRODUCT_TYPE);
        return Mapper.toProductTypeDto(productType.get());
    }

    public ProductTypeDto addNewProductType(ProductTypeDto productTypeDto){
        if (productTypeDto.getName().equals("") || productTypeDto.getName() == null) throw new RestResponseException(HttpStatus.BAD_REQUEST, EntityType.PRODUCT_TYPE);
        ProductType productType = productTypeRepository.save(new ProductType(productTypeDto.getName()));
        productTypeRepository.save(productType);
        return Mapper.toProductTypeDto(productType);
    }

    public void deleteProductTypeById(Long id){
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if (productType.isEmpty()) throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.PRODUCT_TYPE);
        productTypeRepository.deleteById(id);
    }

    public String updateProductType(ProductType newProductType, Long id){
        Long productTypeId = newProductType.getId();
        if (productTypeId == null) throw new ApiRequestException("Id in object is not valid!");
        if (productTypeId != id) throw new ApiRequestException("Id in url is not equal id in object!");
        if (newProductType.getName() == "" || newProductType.getName() == null) throw new ApiRequestException("Name is not valid!");
        ProductType productType = productTypeRepository.findById(id).get();
        productType.setName(newProductType.getName());
        productTypeRepository.save(productType);
        return "Updated";
    }

    public ProductTypeDto updateProductType(ProductTypeDto productTypeDto, Long productId) {
        Optional<ProductType> productType = productTypeRepository.findById(productId);
        if (productType.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.PRODUCT_TYPE);
        }

        productTypeRepository.save(new ProductType(productTypeDto.getName()));
        return productTypeDto;
    }
}