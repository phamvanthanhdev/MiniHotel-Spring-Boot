package com.demo.MiniHotel.modules.hoadon.service;

import com.demo.MiniHotel.model.ChiTietPhieuThue;
import com.demo.MiniHotel.model.ChiTietSuDungDichVu;
import com.demo.MiniHotel.model.HoaDon;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonRequest;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonResponse;

import java.time.LocalDate;
import java.util.List;

public interface IHoaDonService {
    public HoaDon addNewHoaDon(HoaDonRequest request) throws Exception;
    public List<HoaDon> getAllHoaDon();
    public HoaDon getHoaDonById(String id) throws Exception;
    public HoaDon updateHoaDon(HoaDonRequest request, String id) throws Exception;
    public void deleteHoaDon(String id) throws Exception;

    public List<ChiTietPhieuThue> getChiTietPhieuThueBySoHoaDon(String soHoaDon) throws Exception;
    public List<ChiTietSuDungDichVu> getChiTietSuDungDichVuBySoHoaDon(String soHoaDon) throws Exception;

    HoaDonResponse themHoaDonMoi(Integer idNhanVien, Long tongTien, LocalDate ngayTao) throws Exception;

    //ChiTietPhuThu
}
