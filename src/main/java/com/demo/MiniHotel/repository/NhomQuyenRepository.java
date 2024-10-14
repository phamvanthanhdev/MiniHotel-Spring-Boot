package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.NhomQuyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NhomQuyenRepository extends JpaRepository<NhomQuyen, Integer> {
    Optional<NhomQuyen> findByTenNhomQuyen(String tenNhomQuyen);
}
