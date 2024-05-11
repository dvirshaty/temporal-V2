package com.temporal.demos.temporalspringbootdemo.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hsia {
    @Id
    @GeneratedValue
    private Long id;
    private String uuid;
    private String name;
    private String contact;
}
