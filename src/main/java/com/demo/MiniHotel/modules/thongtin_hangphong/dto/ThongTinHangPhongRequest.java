package com.demo.MiniHotel.modules.thongtin_hangphong.dto;

import lombok.Data;

import java.sql.Blob;
import java.time.LocalDate;

@Data
public class ThongTinHangPhongRequest {
    private String tenHangPhong;
    private String moTa;
    private String hinhAnh;
    private String tenKieuPhong;
    private String tenLoaiPhong;
    private float phanTramGiam;
    private Integer giaHienTai;
    private Integer giaKhuyenMai;
}
