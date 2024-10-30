package com.demo.MiniHotel.modules.khachhang.service;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangRequest;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangResponse;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangUpload;

import java.util.List;

public interface IKhachHangService {
    public KhachHangResponse addNewKhachHang(KhachHangRequest request) throws Exception;
    public KhachHangResponse addKhachHangDatPhong(KhachHangRequest request) throws Exception;
    public List<KhachHang> getAllKhachHang();
    public KhachHang getKhachHangById(Integer id) throws Exception;
    public KhachHangResponse getKhachHangBySdt(String sdt) throws Exception;
    public KhachHangResponse getKhachHangResponseByCCCD(String cccd) throws Exception;
    public KhachHang getKhachHangByCccd(String cccd) throws RuntimeException;
    public KhachHangResponse getKhachHangByIdPhieuDat(int idPhieuDat) throws Exception;
    public KhachHang updateKhachHang(KhachHangRequest request, Integer id) throws Exception;
    public void deleteKhachHang(Integer id) throws Exception;

    KhachHangResponse getKhachHangResponseById(Integer id) throws Exception;
    KhachHangResponse updateKhachHangResponse(int id, KhachHangRequest khachHangRequest) throws Exception;
    ApiResponse importKhachHangUpload(KhachHangUpload request) throws Exception;
    void importToanBoKhachHang(List<KhachHangUpload> khachHangUploads) throws Exception;
}
