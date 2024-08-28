package com.demo.MiniHotel.modules.taikhoan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DangKyRequest {
    private String cmnd;
    private String hoTen;
    private String sdt;
    private String diaChi;
    private String email;

    private String tenDangNhap;
    private String matKhau;
}
