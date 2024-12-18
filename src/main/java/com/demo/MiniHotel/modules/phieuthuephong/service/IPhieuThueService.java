package com.demo.MiniHotel.modules.phieuthuephong.service;

import com.demo.MiniHotel.model.ChiTietPhieuThue;
import com.demo.MiniHotel.model.PhieuThuePhong;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.TraPhongRequest;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.TraPhongResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.QuanLyPhieuDatResponse;
import com.demo.MiniHotel.modules.phieuthuephong.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface IPhieuThueService {
    public PhieuThueResponse addNewPhieuThuePhong(PhieuThuePhongRequest request) throws Exception;
    public List<PhieuThuePhong> getAllPhieuThuePhong();
    public PhieuThuePhong getPhieuThuePhongById(Integer id) throws Exception;
    public PhieuThuePhong updatePhieuThuePhong(PhieuThuePhongRequest request, Integer id) throws Exception;
    public void deletePhieuThuePhong(Integer id) throws Exception;
//    public List<ChiTietPhieuThue> getChiTietPhieuThuesByIdPhieuThue(Integer idPhieuThue) throws Exception;
//    public ChiTietPhieuThue addKhachHangToChiTietPhieuThue(ChiTietKhachThueRequest chiTietKhachThueRequest) throws Exception;
//    public ChiTietPhieuThue removeKhachHangInChiTietPhieuThue(DelChiTietKhachThueRequest delChiTietKhachThueRequest) throws Exception;
    public ChiTietPhieuThue getChiTietPhieuThueById(Integer idChiTietPhieuThue) throws Exception;

    PhieuThueResponse getPhieuThuePhongResonseById(Integer id) throws Exception;
    public List<PhieuThueResponse> timKiemPhieuThueTheoCmnd(String cmnd) throws Exception;
    public Boolean kiemTraChiTietPhieuThueCuoiCung(int soLuong, int idPhieuThue) throws Exception;
    ThongTinTraPhongResponse getThongTinTraPhong(ThongTinTraPhongRequest request) throws Exception;
    TraPhongResponse traPhong(TraPhongRequest request) throws Exception;
    PhieuThueResponse capNhatPhanTramGiam(Integer id, int phanTramGiam) throws Exception;
    List<PhieuThueResponse> getPhieuThuePhongTheoTrang(int pageNumber, int pageSize) throws Exception;
    Integer getTongTrangPhieuThuePhong(int pageSize);
    PhieuThueResponse getCapNhatPhieuThuePhongResonseById(Integer id) throws Exception;
    PhieuThueResponse capNhatPhieuThuePhong(CapNhatPhieuThueRequest request) throws Exception;
    List<PhieuThueResponse> getPhieuThueFilter(int luaChon, LocalDate ngayBatDauLoc, LocalDate ngayKetThucLoc, String tenKhachHang, String noiDung) throws Exception;
}
