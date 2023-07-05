package com.example.be.service.cart.impl;

import com.example.be.DTO.cart.CartDTO;
import com.example.be.DTO.cart.CartDetailDTO;
import com.example.be.entity.cart.Cart;
import com.example.be.entity.cart.CartDetail;
import com.example.be.entity.product.Product;
import com.example.be.repository.cart.ICartDetailRepository;
import com.example.be.repository.cart.ICartRepository;
import com.example.be.repository.product.IProductRepository;
import com.example.be.service.cart.ICartDetailService;
import com.example.be.service.product.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartDetailServiceImpl implements ICartDetailService {
    @Autowired
    private ICartDetailRepository iCartDetailRepository;
    @Autowired
    private ICartRepository iCartRepository;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IProductRepository iProductRepository;
    Integer count = 0;
    public void resetCount() {
        count = 0;
    }
    @Override
    public String save(CartDetailDTO cartDetailDTO) {
        Product product = iProductRepository.findById(cartDetailDTO.getProductDTO().getId()).get();
        if (product.getQuantity() < cartDetailDTO.getQuantity()) {
            return "Số lượng không đủ";
        }
        if (count == 0) {
            Cart cart = new Cart();
            iCartRepository.save(cart);
        }
        CartDetail cartDetail = new CartDetail();
        cartDetail.setProduct(new Product());
        BeanUtils.copyProperties(cartDetailDTO.getProductDTO(), cartDetail.getProduct());
        BeanUtils.copyProperties(cartDetailDTO, cartDetail);
        cartDetail.setCart(iCartRepository.findTheLastCart());
        if (product.getQuantity() - cartDetail.getQuantity() < 0) {
            return "Số lượng không đủ";
        }
        product.setQuantity(product.getQuantity() - cartDetail.getQuantity());
        iProductRepository.save(product);
        List<CartDetail> cartDetails = iCartDetailRepository.findAll();
        if (cartDetails.isEmpty()) {
            iCartDetailRepository.save(cartDetail);
            count++;
            return "";
        }
        for (int i = cartDetails.size() - 1; i >= 0; i--) {
            if (count != 0 && cartDetails.get(i).getProduct().equals(product)  && !cartDetails.get(i).isDelete()) {
                cartDetails.get(i).setQuantity(cartDetails.get(i).getQuantity() + cartDetailDTO.getQuantity());
                iCartDetailRepository.save(cartDetails.get(i));
                return "";
            }
        }
        iCartDetailRepository.save(cartDetail);
        count++;
        return "";
    }

    @Override
    public String update(Integer id, Integer quantity) {
        CartDetail cartDetail = iCartDetailRepository.findById(id).get();
        Product product = iProductRepository.findById(cartDetail.getProduct().getId()).get();
        if (product.getQuantity() < quantity) {
            return "Số lượng không đủ";
        }
        product.setQuantity(product.getQuantity() - quantity);
        iProductRepository.save(product);
        if (cartDetail.getQuantity() + quantity <= 0) {
            delete(id);
        }
        cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
        iCartDetailRepository.save(cartDetail);
        return "";
    }

    @Override
    public void delete(int id) {
        CartDetail cartDetail = iCartDetailRepository.findById(id).get();
        cartDetail.setDelete(true);
        Product product = iProductRepository.findById(cartDetail.getProduct().getId()).get();
        product.setQuantity(product.getQuantity() + cartDetail.getQuantity());
        iProductRepository.save(product);
        iCartDetailRepository.save(cartDetail);
    }

    @Override
    public List<CartDetailDTO> findAll() {
        List<CartDetail> cartDetails = iCartDetailRepository.findAllIsDeleteFalse();
        List<CartDetailDTO> cartDetailDTOS = new ArrayList<>();
        CartDetailDTO cartDetailDTO;
        for (CartDetail cartDetail: cartDetails) {
            cartDetailDTO = new CartDetailDTO();
            cartDetailDTO.setProductDTO(iProductService.findById(cartDetail.getProduct().getId()));
            BeanUtils.copyProperties(cartDetail, cartDetailDTO);
            cartDetailDTOS.add(cartDetailDTO);
        }
        return cartDetailDTOS;
    }

    @Override
    public void deleteAll(int id) {
        List<CartDetail> cartDetails = iCartDetailRepository.findAll();
        for (int i = cartDetails.size() - 1; i >= 0; i--) {
            if (cartDetails.get(i).getCart().getId() == id) {
                cartDetails.get(i).setDelete(true);
                iCartDetailRepository.save(cartDetails.get(i));
            }
        }
    }

    @Override
    public Page<CartDetailDTO> findTotalAll(String customerName, Pageable pageable) {
        Page<CartDetail> cartDetails = iCartDetailRepository.findTotalAll(customerName, pageable);
        List<CartDetailDTO> cartDetailDTOS = new ArrayList<>();
        CartDetailDTO cartDetailDTO;
        for (CartDetail cartDetail: cartDetails) {
            cartDetailDTO = new CartDetailDTO();
            cartDetailDTO.setCartDTO(new CartDTO());
            BeanUtils.copyProperties(cartDetail.getCart(), cartDetailDTO.getCartDTO());
            cartDetailDTO.setProductDTO(iProductService.findById(cartDetail.getProduct().getId()));
            BeanUtils.copyProperties(cartDetail, cartDetailDTO);
            cartDetailDTOS.add(cartDetailDTO);
        }
        return new PageImpl<>(cartDetailDTOS, pageable, cartDetails.getTotalElements());
    }
}