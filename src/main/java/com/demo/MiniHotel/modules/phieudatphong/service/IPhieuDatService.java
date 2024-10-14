package com.demo.MiniHotel.modules.phieudatphong.service;

import com.demo.MiniHotel.model.ChiTietPhieuDat;
import com.demo.MiniHotel.model.HoaDon;
import com.demo.MiniHotel.model.PhieuDatPhong;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatRequest;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse2;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietUserResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.*;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongResponse;
import com.demo.MiniHotel.vnpay.PhieuDatThanhToanRequest;

import java.time.LocalDate;
import java.util.List;

public interface IPhieuDatService {
    public PhieuDatPhong datPhongKhachSan(PhieuDatThanhToanRequest request) throws Exception;
    public PhieuDatPhong addNewPhieuDatPhong(PhieuDatRequest request) throws Exception;
    public PhieuDatPhong thanhToanPhieuDat(Integer id, Long tienTamUng) throws Exception;
    public List<PhieuDatPhong> getAllPhieuDatPhong();
    public PhieuDatPhong getPhieuDatPhongById(Integer id) throws Exception;
    public PhieuDatDetailsResponse getPhieuDatDetailsById(Integer id) throws Exception;
    public PhieuDatResponse getPhieuDatResponseById(Integer id) throws Exception;
//    public List<PhieuDatResponse> getPhieuDatPhongByIdKhachHang(Integer idKhachHang) throws Exception;
    public List<PhieuDatUserResponse> getPhieuDatUserByIdKhachHang() throws Exception;
    public PhieuDatPhong updatePhieuDatPhong(PhieuDatRequest request, Integer id) throws Exception;
    public void deletePhieuDatPhong(Integer id) throws Exception;
    public List<ChiTietPhieuDatResponse> getChiTietPhieuDatsByIdPhieuDat(Integer idPhieuDat) throws Exception;
    public List<ChiTietPhieuDatResponse2> getChiTietPhieuDatsByIdPhieuDat2(Integer idPhieuDat) throws Exception;
    public List<ChiTietUserResponse> getChiTietUserByIdPhieuDat(Integer idPhieuDat) throws Exception;
    public List<ChiTietUserResponse> getChiTietUserResponseByIdPhieuDat(Integer idPhieuDat) throws Exception;
    public List<PhieuDatThoiGianResponse> getPhieuDatPhongTheoNgay(LocalDate ngay) ;
    public List<PhieuDatThoiGianResponse> getPhieuDatPhongTheoGian(LocalDate ngayBatDauTim,LocalDate ngayKetThucTim) ;
    public List<PhieuDatResponse> getPhieuDatTheoCMND(String cmnd) throws Exception;
    public PhieuDatPhong huyDatPhong(Integer id) throws Exception;
    public void capNhatDanhSachKhachHang(CapNhatKhachHangResquest req) throws Exception;
    public List<PhieuDatUserResponse> getPhieuDatByCccd(String cccd) throws Exception;
    void boSungChiTietPhieuDat(List<ChiTietPhieuDatRequest> chiTietPhieuDatRequests) throws Exception;
    PhieuDatPhong capNhatTrangThaiPhieuDat(int idPhieuDat, int trangThai) throws Exception;
}
