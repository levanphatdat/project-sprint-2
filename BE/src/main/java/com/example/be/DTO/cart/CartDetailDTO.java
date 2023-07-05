package com.example.be.DTO.cart;

import com.example.be.DTO.productDTO.ProductDTO;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class CartDetailDTO {
    private Integer id;
    @PositiveOrZero(message = "Tổng tiền phải là số dương")
    private Double total;
    @Positive(message = "Hãy chọn số lượng (ít nhất 1 sản phẩm)")
    private Integer quantity;
    private ProductDTO productDTO;
    private CartDTO cartDTO;

    public CartDetailDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public CartDTO getCartDTO() {
        return cartDTO;
    }

    public void setCartDTO(CartDTO cartDTO) {
        this.cartDTO = cartDTO;
    }
}