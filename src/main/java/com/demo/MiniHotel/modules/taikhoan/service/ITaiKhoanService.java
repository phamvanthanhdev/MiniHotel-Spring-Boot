package com.demo.MiniHotel.modules.taikhoan.service;


import com.demo.MiniHotel.model.TaiKhoan;
import com.demo.MiniHotel.modules.taikhoan.dto.TaiKhoanRequest;

import java.util.List;

public interface ITaiKhoanService {
    public TaiKhoan addNewTaiKhoan(TaiKhoanRequest request) throws Exception;
    public List<TaiKhoan> getAllTaiKhoan();
    public TaiKhoan getTaiKhoanById(Integer id) throws Exception;
    public TaiKhoan updateMatKhau(TaiKhoanRequest request) throws Exception;
    public void deleteTaiKhoan(Integer id) throws Exception;
    public TaiKhoan checkLogin(TaiKhoanRequest request);
}
