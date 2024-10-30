package com.demo.MiniHotel.modules.chitiet_phieuthue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietKhachThueResponse {
    private int idChiTietPhieuThue;
    private int idKhachHang;
    private String maPhong;
    private String hoTen;
    private String cccd;
    private String sdt;
    private LocalDate ngayDen;
    private LocalDate ngayDi;
}
