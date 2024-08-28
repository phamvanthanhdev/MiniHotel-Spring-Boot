package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.HoaDon;
import com.demo.MiniHotel.model.HoaDonNgay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HoaDonNgayRepository extends JpaRepository<HoaDonNgay, String> {
}
