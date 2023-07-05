package com.example.be.DTO.productDTO;

import javax.persistence.Entity;
import javax.persistence.Table;


public class ProductImgDTO {
    private Integer id;
    private String url;
    private ProductDTO productDTO;

    public ProductImgDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }
}
