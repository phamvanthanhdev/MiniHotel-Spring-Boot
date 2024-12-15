package com.demo.MiniHotel.modules.phieudatphong.service;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.ChiTietPhieuDat;
import com.demo.MiniHotel.model.PhieuDatPhong;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatRequest;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse2;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietUserResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.*;
import com.demo.MiniHotel.vnpay.PhieuDatThanhToanRequest;
import jakarta.mail.MessagingException;

import java.time.LocalDate;
import java.util.List;

public interface IPhieuDatService {
//    public PhieuDatPhong datPhongKhachSan(PhieuDatThanhToanRequest request) throws Exception;
    ApiResponse datPhongKhachSanBySP(PhieuDatThanhToanRequest request) throws Exception;
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
    public List<PhieuDatThoiGianResponse> getPhieuDatPhongTheoNgay(PhieuDatTheoNgayRequest request) ;
    public List<PhieuDatThoiGianResponse> getPhieuDatPhongTheoGian(LocalDate ngayBatDauTim,LocalDate ngayKetThucTim) ;
    public List<PhieuDatResponse> getPhieuDatTheoCMND(String cmnd) throws Exception;
    public PhieuDatPhong huyDatPhong(HuyDatRequest request) throws Exception;
    public void capNhatDanhSachKhachHang(CapNhatKhachHangResquest req) throws Exception;
    public List<PhieuDatUserResponse> getPhieuDatByCccd(String cccd) throws Exception;
    void boSungChiTietPhieuDat(List<ChiTietPhieuDatRequest> chiTietPhieuDatRequests) throws Exception;
    PhieuDatPhong capNhatTrangThaiPhieuDat(int idPhieuDat, int trangThai) throws Exception;
    List<QuanLyPhieuDatResponse> getPhieuDatPhongTheoTrang(int pageNumber, int pageSize) throws Exception;
    Integer getTongTrangPhieuDatPhong(int pageSize);

    CapNhatPhieuDatResponse getCapNhatPhieuDatById(Integer id) throws Exception;

    PhieuDatPhong capNhatPhieuDat(CapNhatPhieuDatRequest request) throws Exception;

    List<PhieuDatUserResponse> getPhieuDatPhongKhachHangTheoTrang(int pageNumber, int pageSize) throws Exception;
    Integer getTongTrangPhieuDatPhongKhachHang(int pageSize);

    List<PhieuDatUserResponse> getPhieuDatPhongCccdTheoTrang(int pageNumber, int pageSize, String cccd) throws Exception;
    Integer getTongTrangPhieuDatPhongCccd(int pageSize, String cccd);
    List<QuanLyPhieuDatResponse> getPhieuDatFilter(int luaChon, LocalDate ngayBatDauLoc, LocalDate ngayKetThucLoc, int trangThai, String noiDung) throws Exception;
    void tuDongHuyPhieuDats() throws Exception;
    void sendMailThongBaoDatPhong(int idPhieuDat, String emailKhachHang, List<ChiTietPhieuDat> chiTietPhieuDats) throws Exception;
    List<QuanLyPhieuDatResponse> getPhieuDatKhachHangFilter(int luaChon, LocalDate ngayBatDauLoc, LocalDate ngayKetThucLoc, int trangThai, Integer idPhieuDat) throws Exception;
    ApiResponse khoiPhucPhieuDat(int idPhieuDat) throws Exception;

    PhieuDatPhong datPhongKhachSan2(PhieuDatRequest request) throws Exception;
    public ApiResponse bookingBySP(PhieuDatRequest request) throws Exception;
//    public boolean runSPBooking(String idHangPhongList, String soLuongList, String donGiaList,
//                                int idPhieuDat, int idKhachHang, Integer idNhanVien, LocalDate ngayNhanPhong,
//                                LocalDate ngayTraPhong, String ghiChu, LocalDate ngayTao, long tienTamUng,
//                                int trangThaiHuy, long tienTraLai);
}
