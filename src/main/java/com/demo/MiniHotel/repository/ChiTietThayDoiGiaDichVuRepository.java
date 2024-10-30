package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.embedded.IdChiTietThayDoiGiaDichVuEmb;
import com.demo.MiniHotel.model.ChiTietThayDoiGiaDichVu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ChiTietThayDoiGiaDichVuRepository extends JpaRepository<ChiTietThayDoiGiaDichVu, IdChiTietThayDoiGiaDichVuEmb> {
    List<ChiTietThayDoiGiaDichVu> findByIdChiTietThayDoiGiaDichVuEmb_IdDichVuAndNgayApDung(
            Integer idDichVu, LocalDate ngayApDung);
    boolean existsByIdChiTietThayDoiGiaDichVuEmb_IdDichVuAndNgayApDung(
            Integer idDichVu, LocalDate ngayApDung);
    List<ChiTietThayDoiGiaDichVu> findByIdChiTietThayDoiGiaDichVuEmb_IdDichVuAndIdChiTietThayDoiGiaDichVuEmb_NgayCapNhat(
            Integer idDichVu, LocalDate ngayCapNhat);
    boolean existsByIdChiTietThayDoiGiaDichVuEmb_IdDichVuAndIdChiTietThayDoiGiaDichVuEmb_NgayCapNhat(
            Integer idDichVu, LocalDate ngayCapNhat);

    List<ChiTietThayDoiGiaDichVu> findByIdChiTietThayDoiGiaDichVuEmb_IdDichVu(Integer idDichVu);
}
