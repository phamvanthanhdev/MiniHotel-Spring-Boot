package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.PhieuDatPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhieuDatPhongRepository extends JpaRepository<PhieuDatPhong, Integer> {
    List<PhieuDatPhong> findByKhachHang_IdKhachHang(int idKhachHang);
}