package com.demo.MiniHotel.modules.hangphong.dto;

import lombok.Data;

@Data
public class HangPhongRequest {
    private Integer idLoaiPhong;
    private Integer idKieuPhong;
    private String tenHangPhong;
    private String moTa;
    private String hinhAnh;
}
