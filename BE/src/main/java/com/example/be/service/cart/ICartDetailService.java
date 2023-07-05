package com.example.be.service.cart;

import com.example.be.DTO.cart.CartDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICartDetailService {
    String save(CartDetailDTO cartDetailDTO);
    String update(Integer id, Integer quantity);
    void delete(int id);
    List<CartDetailDTO> findAll();
    void deleteAll(int id);
    Page<CartDetailDTO> findTotalAll(String customerName, Pageable pageable);
}