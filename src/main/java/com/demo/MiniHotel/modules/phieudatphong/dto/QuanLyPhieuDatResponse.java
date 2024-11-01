package com.demo.MiniHotel.modules.phieudatphong.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuanLyPhieuDatResponse {
    Integer idPhieuDat;

    LocalDate ngayBatDau;
    LocalDate ngayTraPhong;
    LocalDate ngayTao;

    Integer idKhachHang;
    String tenKhachHang;

    Integer idNhanVien;

    Long tienTamUng;
    Long tongTien;
    Long tienTra;

    Integer trangThaiHuy;
}
