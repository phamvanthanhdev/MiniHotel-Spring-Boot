package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.PhieuDatPhong;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhieuDatPhongPagingRepository extends PagingAndSortingRepository<PhieuDatPhong, Integer> {
    List<PhieuDatPhong> findByKhachHang_IdKhachHang(int idKhachHang, Pageable pageable);
    List<PhieuDatPhong> findByKhachHang_Cmnd(String cmnd, Pageable pageable);
    @Query("SELECT p FROM PhieuDatPhong p WHERE p.khachHang.cmnd = :cmndKhachHang AND p.ngayBatDau >= CURRENT_DATE")
    List<PhieuDatPhong> findByKhachHangForCheckin(@Param("cmndKhachHang") String cmndKhachHang, Pageable pageable);
}
