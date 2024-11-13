package com.demo.MiniHotel.modules.khachhang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHangProfileResquest {
    private int idKhachHang;
    private String cmnd;
    private String hoTen;
    private String sdt;
    private String diaChi;
    private String email;
    private boolean gioiTinh;
    private LocalDate ngaySinh;
    private String matKhauCu;
    private String matKhauMoi;
}
