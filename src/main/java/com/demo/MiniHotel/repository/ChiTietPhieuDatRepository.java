package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.embedded.IdChiTietPhieuDatEmb;
import com.demo.MiniHotel.model.ChiTietPhieuDat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietPhieuDatRepository extends JpaRepository<ChiTietPhieuDat, IdChiTietPhieuDatEmb> {
    List<ChiTietPhieuDat> findByPhieuDatPhong_IdPhieuDat(Integer idPhieuDat);
//    List<ChiTietPhieuDat> findByIdPhieuDatPhongIdPhieuDat(Integer idPhieuDat);
//    void deleteByIdPhieuDat(Integer idPhieuDat);
}
