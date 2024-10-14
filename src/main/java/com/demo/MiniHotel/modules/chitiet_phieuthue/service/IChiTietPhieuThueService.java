package com.demo.MiniHotel.modules.chitiet_phieuthue.service;


import com.demo.MiniHotel.model.ChiTietPhieuThue;
import com.demo.MiniHotel.model.HoaDon;
import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.model.PhieuThuePhong;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietKhachThueResponse;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueRequest;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueResponse;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.TraPhongRequest;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.phieuthuephong.dto.ChiTietKhachThueRequest;
import com.demo.MiniHotel.modules.phieuthuephong.dto.DelChiTietKhachThueRequest;

import java.time.LocalDate;
import java.util.List;

public interface IChiTietPhieuThueService {
    public ChiTietPhieuThue addNewChiTietPhieuThue(ChiTietPhieuThueRequest request) throws Exception;
    public void addListChiTietPhieuThue(List<ChiTietPhieuThueRequest> requests) throws Exception;
    public List<ChiTietPhieuThue> getAllChiTietPhieuThue();
    public ChiTietPhieuThue getChiTietPhieuThueById(Integer id) throws Exception;
    public ChiTietPhieuThueResponse getChiTietPhieuThueResponseById(Integer id) throws Exception;
    public List<ChiTietPhieuThue> getChiTietPhieuThueByIdPhieuThue(Integer idPhieuThue) throws Exception;
    public ChiTietPhieuThue updateChiTietPhieuThue(ChiTietPhieuThueRequest request, Integer id) throws Exception;
    public void deleteChiTietPhieuThue(Integer id) throws Exception;
    public void deleteChiTietPhieuThueByIdPhieuThue(Integer idPhieuThue) throws Exception;
    public ChiTietPhieuThue addKhachHangToChiTietPhieuThue(ChiTietKhachThueRequest khachThueRequest) throws Exception;
    public ChiTietPhieuThue removeKhachHangInChiTietPhieuThue(DelChiTietKhachThueRequest request) throws Exception;
    List<ChiTietKhachThueResponse> getKhachThueHienTai() throws Exception;
    public HoaDon getHoaDonByChiTietPhieuThue(Integer id) throws Exception;
    public ChiTietPhieuThue addHoaDonToChiTietPhieuThue(Integer id, ChiTietPhieuThueRequest request) throws Exception;

    ChiTietPhieuThueResponse updateNgayDi(Integer id, LocalDate ngayDi) throws Exception;

    HoaDonResponse traPhongKhachSan(Integer idNhanVien, Long tongTien, LocalDate ngayTao, Integer idChiTietPhieuThue) throws Exception ;

    List<ChiTietPhieuThueResponse> getChiTietPhieuThueResponseByIdPhieuThue(int idPhieuThue) throws Exception;

    HoaDonResponse traPhongKhachSanKhachDoan(TraPhongRequest request) throws Exception;

    boolean doiPhong(int idChiTietPhieuThue, String maPhong) throws Exception;
    ChiTietPhieuThue themChiTietPhieuThue(ChiTietPhieuThueRequest request) throws Exception;
    ChiTietPhieuThueResponse thayDoiNgayTraPhong(Integer id, LocalDate ngayTraPhong) throws Exception;
    ChiTietPhieuThue themHoaDonToChiTietPhieuThue(Integer id, String soHoaDon) throws Exception;
}
