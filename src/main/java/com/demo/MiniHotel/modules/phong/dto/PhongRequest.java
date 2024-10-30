package com.demo.MiniHotel.modules.phong.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PhongRequest {
    private String maPhong;
    private Integer tang;
    private String moTa;
//    private LocalDate ngayTao;
//    private LocalDate ngayCapNhat;
    private int idHangPhong;
    private int idTrangThai;
}
