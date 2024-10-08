package com.demo.MiniHotel.modules.khachhang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangRequest {
    private String cmnd;
    private String hoTen;
    private String sdt;
    private String diaChi;
    private String email;

    private Integer idTaiKhoan;
}
