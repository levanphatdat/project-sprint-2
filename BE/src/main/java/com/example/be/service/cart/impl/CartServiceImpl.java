package com.example.be.service.cart.impl;

import com.example.be.DTO.cart.CartDTO;
import com.example.be.entity.cart.Cart;
import com.example.be.repository.cart.ICartRepository;
import com.example.be.service.cart.ICartDetailService;
import com.example.be.service.cart.ICartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private ICartRepository iCartRepository;
    @Autowired
    private ICartDetailService iCartDetailService;

    @Override
    public void update(CartDTO cartDTO) {

        Cart cart = iCartRepository.findTheLastCart();
        cartDTO.setId(cart.getId());
        BeanUtils.copyProperties(cartDTO, cart);
        cart.setDelete(true);
        iCartDetailService.deleteAll(cart.getId());
        iCartRepository.save(cart);

    }
}