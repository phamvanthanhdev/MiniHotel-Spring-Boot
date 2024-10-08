package com.demo.MiniHotel.modules.taikhoan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanRequest {
    private String tenDangNhap;
    private String matKhau;
    private Integer idNhomQuyen;
}
