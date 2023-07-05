package com.example.be.entity.product;

import com.example.be.entity.cart.CartDetail;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    private Integer quantity;
    private String volume;
    private String brand;

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    @JsonBackReference
    private ProductType productType;
    @OneToMany (mappedBy = "product")
    @JsonManagedReference
    private Set<ProductImg> productImgs;
    @OneToMany (mappedBy = "product")
    @JsonManagedReference
    private Set<CartDetail> cartDetails;

    public Product() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Set<ProductImg> getProductImgs() {
        return productImgs;
    }

    public void setProductImgs(Set<ProductImg> productImgs) {
        this.productImgs = productImgs;
    }

    public Set<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(Set<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }
}
