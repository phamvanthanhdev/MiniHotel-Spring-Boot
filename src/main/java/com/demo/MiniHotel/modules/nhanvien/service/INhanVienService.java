package com.demo.MiniHotel.modules.nhanvien.service;

import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienDetailsResponse;
import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienRequest;
import com.demo.MiniHotel.model.NhanVien;
import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienResponse;

import java.util.List;

public interface INhanVienService {
    public NhanVienDetailsResponse addNewNhanVien(NhanVienRequest request) throws Exception;
    public List<NhanVienResponse> getAllNhanVienResponse();
    public List<NhanVien> getAllNhanVien();
    public NhanVien getNhanVienById(Integer id) throws Exception;
    public NhanVienDetailsResponse updateNhanVien(NhanVienRequest request, Integer id) throws Exception;
    public void deleteNhanVien(Integer id) throws Exception;
    public NhanVien getNhanVienByToken() throws Exception;
    public NhanVienDetailsResponse getNhanVienDetailsById(Integer id) throws Exception;
}
