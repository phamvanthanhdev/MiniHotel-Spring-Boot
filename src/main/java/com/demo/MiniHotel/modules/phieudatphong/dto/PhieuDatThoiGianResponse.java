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
    private LocalDate ngayNhanPhong;
    private LocalDate ngayTraPhong;
    private LocalDate ngayTao;
    private long soNgay;
    private String ghiChu;
    private Long tienTamUng;
    private Long tongTien;
    private String tenKhachHang;
    private String cmnd;
    private String sdt;
    private int trangThai;
}
