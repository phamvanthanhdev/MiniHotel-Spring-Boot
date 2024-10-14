package com.demo.MiniHotel.modules.khachhang.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private boolean gioiTinh;
    private LocalDate ngaySinh;
}
