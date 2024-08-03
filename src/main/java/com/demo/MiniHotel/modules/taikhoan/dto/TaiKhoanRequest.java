package com.demo.MiniHotel.modules.taikhoan.dto;

import lombok.Data;

@Data
public class TaiKhoanRequest {
    private String tenDangNhap;
    private String matKhau;
    private Integer idNhomQuyen;
}
