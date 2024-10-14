package com.demo.MiniHotel.modules.phieudatphong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuDatUserResponse {
    private Integer idPhieuDat;
    private LocalDate ngayBatDau;
    private LocalDate ngayTraPhong;
    private String ghiChu;
    private LocalDate ngayTao;
    private Long tienTamUng;
    private Integer idKhachHang;
    private Integer idNhanVien;
    private Integer trangThaiHuy;
    private Long tongTien;
}
