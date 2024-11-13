package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.embedded.IdChiTietPhieuDatEmb;
import com.demo.MiniHotel.model.ChiTietPhieuDat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChiTietPhieuDatRepository extends JpaRepository<ChiTietPhieuDat, Integer/*IdChiTietPhieuDatEmb*/> {
    List<ChiTietPhieuDat> findByPhieuDatPhong_IdPhieuDat(Integer idPhieuDat);
//    List<ChiTietPhieuDat> findByIdPhieuDatPhongIdPhieuDat(Integer idPhieuDat);
//    void deleteByIdPhieuDat(Integer idPhieuDat);

    Optional<ChiTietPhieuDat> findByPhieuDatPhong_IdPhieuDatAndHangPhong_IdHangPhongAndDonGia(Integer idPhieuDat, Integer idHangPhong, long donGia);
}
