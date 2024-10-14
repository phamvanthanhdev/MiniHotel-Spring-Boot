package com.demo.MiniHotel.modules.phieudatphong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuDatThoiGianResponse {
    private Integer idPhieuDat;
    private LocalDate ngayBatDau;
    private LocalDate ngayTraPhong;
    private String ghiChu;
    private LocalDate ngayTao;
    private Long tienTamUng;
    private Integer idKhachHang;
    private String tenKhachHang;
    private String cmnd;
    private Integer idNhanVien;
    private Integer trangThaiHuy;
}
