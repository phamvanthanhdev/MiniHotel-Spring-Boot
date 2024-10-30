package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.embedded.IdChiTietThayDoiGiaPhuThuEmb;
import com.demo.MiniHotel.model.ChiTietThayDoiGiaPhuThu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ChiTietThayDoiGiaPhuThuRepository extends JpaRepository<ChiTietThayDoiGiaPhuThu, IdChiTietThayDoiGiaPhuThuEmb> {
    boolean existsByIdChiTietThayDoiGiaPhuThuEmb_IdPhuThuAndNgayApDung(
            Integer idPhuThu, LocalDate ngayApDung);

    List<ChiTietThayDoiGiaPhuThu> findByIdChiTietThayDoiGiaPhuThuEmb_IdPhuThu(Integer idPhuThu);
}
