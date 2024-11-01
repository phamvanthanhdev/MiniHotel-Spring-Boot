package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.embedded.IdChiTietSuDungDichVuEmb;
import com.demo.MiniHotel.model.ChiTietPhieuThue;
import com.demo.MiniHotel.model.ChiTietSuDungDichVu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietSuDungDichVuRepository extends JpaRepository<ChiTietSuDungDichVu, IdChiTietSuDungDichVuEmb> {
    List<ChiTietSuDungDichVu> findByChiTietPhieuThue_IdChiTietPhieuThue(Integer idChiTietPhieuThue);
    List<ChiTietSuDungDichVu> findByChiTietPhieuThue_PhieuThuePhong_IdPhieuThue(Integer idPhieuThue);
}
