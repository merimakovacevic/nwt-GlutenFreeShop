package product.microservice.productmicroservice.dto.mapper;

import product.microservice.productmicroservice.dto.model.ProductDto;
import product.microservice.productmicroservice.model.Product;

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
}