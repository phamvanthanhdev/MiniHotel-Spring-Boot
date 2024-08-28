package com.demo.MiniHotel.modules.khachhang.service;

import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangRequest;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangResponse;

import java.util.List;

public interface IKhachHangService {
    public KhachHangResponse addNewKhachHang(KhachHangRequest request) throws Exception;
    public List<KhachHang> getAllKhachHang();
    public KhachHang getKhachHangById(Integer id) throws Exception;
    public KhachHangResponse getKhachHangBySdt(String sdt) throws Exception;
    public KhachHangResponse getKhachHangByIdPhieuDat(int idPhieuDat) throws Exception;
    public KhachHang updateKhachHang(KhachHangRequest request, Integer id) throws Exception;
    public void deleteKhachHang(Integer id) throws Exception;

    KhachHangResponse getKhachHangResponseById(Integer id) throws Exception;
    KhachHangResponse updateKhachHangResponse(int id, KhachHangRequest khachHangRequest) throws Exception;
}
