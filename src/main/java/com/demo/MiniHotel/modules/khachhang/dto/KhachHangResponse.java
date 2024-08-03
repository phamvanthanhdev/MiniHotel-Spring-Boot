package com.demo.MiniHotel.modules.khachhang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangResponse {
    private int idKhachHang;
    private String cmnd;
    private String hoTen;
    private String sdt;
    private String diaChi;
    private String email;
}
