package com.example.be.repository.cart;

import com.example.be.entity.cart.CartDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICartDetailRepository extends JpaRepository<CartDetail, Integer> {
    @Query(value = "select * from cart_detail where is_delete = false", nativeQuery = true)
    List<CartDetail> findAllIsDeleteFalse();

    @Query(value = "select * from cart_detail cd join cart c on cd.cart_id = c.id " +
            "where cd.is_delete = true and c.customer_name=:customerName", nativeQuery = true)
    Page<CartDetail> findTotalAll(@Param("customerName") String customerName, Pageable pageable);
}