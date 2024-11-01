package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.embedded.IdChiTietPhuThuEmb;
import com.demo.MiniHotel.embedded.IdChiTietSuDungDichVuEmb;
import com.demo.MiniHotel.model.ChiTietPhuThu;
import com.demo.MiniHotel.model.ChiTietSuDungDichVu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietPhuThuRepository extends JpaRepository<ChiTietPhuThu, IdChiTietPhuThuEmb> {
    List<ChiTietPhuThu> findByChiTietPhieuThue_IdChiTietPhieuThue(Integer idChiTietPhieuThue);
    List<ChiTietPhuThu> findByChiTietPhieuThue_PhieuThuePhong_IdPhieuThue(Integer idPhieuThue);
}
