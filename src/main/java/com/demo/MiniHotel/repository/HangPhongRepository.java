package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.embedded.IdHangPhongEmb;
import com.demo.MiniHotel.model.HangPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HangPhongRepository extends JpaRepository<HangPhong, Integer> {
    Optional<HangPhong> findByIdHangPhong(Integer id);
    void deleteByIdHangPhong(Integer id);
    boolean existsByTenHangPhong(String tenHangPhong);
}
