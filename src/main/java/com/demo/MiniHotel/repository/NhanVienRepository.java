package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.NhanVien;
import com.demo.MiniHotel.model.PhieuDatPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    Optional<NhanVien> findByTaiKhoan_TenDangNhap(String tenDangNhap);
}
