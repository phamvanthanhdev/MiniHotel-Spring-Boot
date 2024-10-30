package com.demo.MiniHotel.modules.chitiet_phieuthue.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChiTietPhieuThueRequest {
    private String maPhong;
    private Integer idPhieuThue;
    private String soHoaDon;
    private LocalDate ngayDen;
    private LocalDate ngayDi;
    private Long donGia;
    private Boolean daThanhToan;
    private Long tienGiamGia;
}
