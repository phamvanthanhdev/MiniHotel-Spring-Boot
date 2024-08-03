package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.ChiTietPhieuThue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietPhieuThueRepository extends JpaRepository<ChiTietPhieuThue, Integer> {
    List<ChiTietPhieuThue> findByPhieuThuePhong_IdPhieuThue(Integer idPhieuThue);
}
