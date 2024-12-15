package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.embedded.IdChiTietPhuThuEmb;
import com.demo.MiniHotel.embedded.IdChiTietSuDungDichVuEmb;
import com.demo.MiniHotel.model.ChiTietPhuThu;
import com.demo.MiniHotel.model.ChiTietSuDungDichVu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChiTietPhuThuRepository extends JpaRepository<ChiTietPhuThu, Integer> {
    List<ChiTietPhuThu> findByChiTietPhieuThue_IdChiTietPhieuThue(Integer idChiTietPhieuThue);
    List<ChiTietPhuThu> findByChiTietPhieuThue_PhieuThuePhong_IdPhieuThue(Integer idPhieuThue);

    Optional<ChiTietPhuThu> findByChiTietPhieuThue_IdChiTietPhieuThueAndPhuThu_IdPhuThuAndDonGiaAndDaThanhToan
            (int idChiTietPhieuThue, int idPhuThu, long donGia, boolean daThanhToan);
}
