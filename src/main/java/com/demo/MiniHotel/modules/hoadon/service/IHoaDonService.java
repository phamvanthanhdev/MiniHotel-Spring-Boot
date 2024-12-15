package com.demo.MiniHotel.modules.hoadon.service;

import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.hoadon.dto.DoanhThuQuyResponse;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonDetailsResponse;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonRequest;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonResponse;

import java.time.LocalDate;
import java.util.List;

public interface IHoaDonService {
    public HoaDon addNewHoaDon(HoaDonRequest request) throws Exception;
    public List<HoaDon> getAllHoaDon();
    public HoaDon getHoaDonById(String id) throws Exception;
    HoaDonDetailsResponse getHoaDonDetailsById(String id) throws Exception;
    public HoaDon updateHoaDon(HoaDonRequest request, String id) throws Exception;
    public void deleteHoaDon(String id) throws Exception;

    public List<ChiTietPhieuThue> getChiTietPhieuThueBySoHoaDon(String soHoaDon) throws Exception;
    public List<ChiTietSuDungDichVu> getChiTietSuDungDichVuBySoHoaDon(String soHoaDon) throws Exception;

    HoaDonResponse themHoaDonMoi(Long tongTien, int idPhieuThue) throws Exception;

    List<HoaDonNgay> getHoaDonNgaysHienTai();
    List<HoaDonNgay> getHoaDonNgaysTheoNgay(LocalDate ngay);
    List<DoanhThuTheoNgay> getDoanhThuTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc);
    List<DoanhThuTheoThang> getDoanhThuTheoThang(int thangBatDau, int thangKetThuc, int nam);
    List<DoanhThuQuyResponse> getDoanhThuTheoQuy(int quyBatDau, int quyKetThuc, int nam);

}
