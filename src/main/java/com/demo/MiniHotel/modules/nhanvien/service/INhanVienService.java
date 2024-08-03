package com.demo.MiniHotel.modules.nhanvien.service;

import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienRequest;
import com.demo.MiniHotel.model.NhanVien;

import java.util.List;

public interface INhanVienService {
    public NhanVien addNewNhanVien(NhanVienRequest request) throws Exception;
    public List<NhanVien> getAllNhanVien();
    public NhanVien getNhanVienById(Integer id) throws Exception;
    public NhanVien updateNhanVien(NhanVienRequest request, Integer id) throws Exception;
    public void deleteNhanVien(Integer id) throws Exception;
}
