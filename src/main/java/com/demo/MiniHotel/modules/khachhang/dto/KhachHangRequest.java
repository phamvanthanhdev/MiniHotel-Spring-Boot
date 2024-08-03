package com.demo.MiniHotel.modules.khachhang.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class KhachHangRequest {
    private String cmnd;
    private String hoTen;
    private String sdt;
    private String diaChi;
    private String maSoThue;
    private String email;

    private Integer idTaiKhoan;
}
