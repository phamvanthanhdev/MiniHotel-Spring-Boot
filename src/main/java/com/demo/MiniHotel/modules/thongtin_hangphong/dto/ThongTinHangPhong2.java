package com.demo.MiniHotel.modules.thongtin_hangphong.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinHangPhong2 {
    private Integer idHangPhong;
    private String tenHangPhong;
    private String moTa;
    private Blob hinhAnh;
    private String tenKieuPhong;
    private String tenLoaiPhong;
    private Integer soNguoiToiDa;
    private Float phanTramGiam;
    private Long giaGoc;
    private Long giaKhuyenMai;
    private Integer soLuongTrong;
}
