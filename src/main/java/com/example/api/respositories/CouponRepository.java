package com.example.api.respositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.domain.coupon.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    
}
