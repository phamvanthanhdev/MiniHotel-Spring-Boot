package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.embedded.IdChiTietKhuyenMaiEmb;
import com.demo.MiniHotel.model.ChiTietKhuyenMai;
import com.demo.MiniHotel.model.ChuongTrinhKhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChiTietKhuyenMaiRepository extends JpaRepository<ChiTietKhuyenMai, IdChiTietKhuyenMaiEmb> {
}
