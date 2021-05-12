package com.example.ratingmicroservice.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@Builder
@ApiModel
@AllArgsConstructor
public class ProductDto implements Serializable {
    private Long id;

    private String name;

    private String description;

    private String productTypeName;

    private List<String> urls;
}
