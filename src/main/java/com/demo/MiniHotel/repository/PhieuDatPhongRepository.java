package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.PhieuDatPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface PhieuDatPhongRepository extends JpaRepository<PhieuDatPhong, Integer> {
    List<PhieuDatPhong> findByKhachHang_IdKhachHang(int idKhachHang);
    List<PhieuDatPhong> findByNgayBatDau(LocalDate ngayBatDau);
    long countByKhachHang_IdKhachHang(int idKhachHang);
    long countByKhachHang_Cmnd(String cmnd);
}
