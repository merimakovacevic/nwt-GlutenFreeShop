package product.microservice.productmicroservice.dto.mapper;

import product.microservice.productmicroservice.dto.model.ImageDto;
import product.microservice.productmicroservice.dto.model.ProductDto;
import product.microservice.productmicroservice.dto.model.ProductTypeDto;
import product.microservice.productmicroservice.model.Image;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;

import java.util.stream.Collectors;

public class Mapper {
    public static ProductDto toProductDto(Product product) {
        return new ProductDto()
                .setId(product.getId())
                .setDescription(product.getDescription())
                .setName(product.getName())
                .setUrls(product.getImages().stream().map(image -> image.getUrl()).collect(Collectors.toList()))
                .setProductTypeName(product.getProductType().getName());
    }
    public static ImageDto toImageDto(Image image) {
        return new ImageDto()
                .setId(image.getId())
                .setUrl(image.getUrl());
    }
    public static ProductTypeDto toProductTypeDto(ProductType productType) {
        return new ProductTypeDto()
                .setId(productType.getId())
                .setName(productType.getName());
    }
}