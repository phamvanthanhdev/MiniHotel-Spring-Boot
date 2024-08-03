package com.demo.MiniHotel.modules.thongtin_phong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhongTrongResponse {
    private String maPhong;
    private int idHangPhong;
    private int tang;
    private String trangThai;
    private long donGia;
}
