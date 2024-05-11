package com.temporal.demos.temporalspringbootdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HsiaDto {

    private Long id;
    private String uuid;
    private String name;
    private String contact;
    private Dog dog;
}
