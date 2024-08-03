package com.demo.MiniHotel.modules.phieudatphong.service;

import com.demo.MiniHotel.model.ChiTietPhieuDat;
import com.demo.MiniHotel.model.HoaDon;
import com.demo.MiniHotel.model.PhieuDatPhong;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse2;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatRequest;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatThoiGianResponse;

import java.time.LocalDate;
import java.util.List;

public interface IPhieuDatService {
    public PhieuDatPhong addNewPhieuDatPhong(PhieuDatRequest request) throws Exception;
    public List<PhieuDatPhong> getAllPhieuDatPhong();
    public PhieuDatPhong getPhieuDatPhongById(Integer id) throws Exception;
    public PhieuDatResponse getPhieuDatResponseById(Integer id) throws Exception;
    public List<PhieuDatResponse> getPhieuDatPhongByIdKhachHang(Integer idKhachHang) throws Exception;
    public PhieuDatPhong updatePhieuDatPhong(PhieuDatRequest request, Integer id) throws Exception;
    public void deletePhieuDatPhong(Integer id) throws Exception;
    public List<ChiTietPhieuDatResponse> getChiTietPhieuDatsByIdPhieuDat(Integer idPhieuDat) throws Exception;
    public List<ChiTietPhieuDatResponse2> getChiTietPhieuDatsByIdPhieuDat2(Integer idPhieuDat) throws Exception;
    public List<PhieuDatThoiGianResponse> getPhieuDatPhongTheoNgay(LocalDate ngay) ;
}
