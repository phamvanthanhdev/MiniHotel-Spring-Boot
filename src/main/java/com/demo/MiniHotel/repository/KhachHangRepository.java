package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    Optional<KhachHang> findBySdt(String sdt);
    Optional<KhachHang> findByCmnd(String cmnd);
    boolean existsByCmnd(String cmnd);
    boolean existsByEmail(String email);
}
