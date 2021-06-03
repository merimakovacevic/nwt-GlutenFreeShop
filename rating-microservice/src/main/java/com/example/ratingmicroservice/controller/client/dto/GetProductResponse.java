package com.example.ratingmicroservice.controller.client.dto;

import com.example.ratingmicroservice.dto.model.ProductDto;
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
public class GetProductResponse implements Serializable {

    private String status;

    private ProductDto result;
}
