package com.demo.MiniHotel.modules.taikhoan.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoanKhachHangResponse {
    private String tenDangNhap;
//    private String matKhau;
    private String cmnd;
    private String hoTen;
    private String sdt;
    private String diaChi;
    private String email;
    private boolean gioiTinh;
    private LocalDate ngaySinh;
}
