package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.DichVu;
import com.demo.MiniHotel.model.PhuThu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhuThuRepository extends JpaRepository<PhuThu, Integer> {
}
