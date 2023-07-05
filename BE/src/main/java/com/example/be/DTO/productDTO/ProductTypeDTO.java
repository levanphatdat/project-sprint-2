package com.example.be.DTO.productDTO;

import java.util.Set;

public class ProductTypeDTO {
    private Integer id;
    private String name;
    private Set<ProductDTO> productDTOS;

    public ProductTypeDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProductDTO> getProductDTOS() {
        return productDTOS;
    }

    public void setProductDTOS(Set<ProductDTO> productDTOS) {
        this.productDTOS = productDTOS;
    }
}
