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
                .setDescription(product.getDescription())
                .setName(product.getName())
                .setStock(product.getStock())
                .setPrice(product.getPrice())
                .setProductTypeName(product.getProductType().getName());
    }
    public static ImageDto toImageDto(Image image) {
        return new ImageDto()
                .setUrl(image.getUrl())
                .setProductId(image.getProduct().getId());
    }
    public static ProductTypeDto toProductTypeDto(ProductType productType) {
        return new ProductTypeDto()
                .setName(productType.getName());
    }
}