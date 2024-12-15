package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.PhieuDatPhong;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PhieuDatPhongRepository extends JpaRepository<PhieuDatPhong, Integer> {
    List<PhieuDatPhong> findByKhachHang_IdKhachHang(int idKhachHang);
    List<PhieuDatPhong> findByNgayBatDau(LocalDate ngayBatDau);
    long countByKhachHang_IdKhachHang(int idKhachHang);
    long countByKhachHang_Cmnd(String cmnd);
    @Query("SELECT COUNT(*) FROM PhieuDatPhong p WHERE p.khachHang.cmnd = :cmndKhachHang AND p.ngayBatDau >= CURRENT_DATE")
    long countByKhachHangForCheckin(@Param("cmndKhachHang") String cmndKhachHang);
}
