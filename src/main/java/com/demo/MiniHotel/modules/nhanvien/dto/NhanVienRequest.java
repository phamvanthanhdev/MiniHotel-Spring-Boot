package com.demo.MiniHotel.modules.nhanvien.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NhanVienRequest {
    private String cccd;
    private String hoTen;
    private boolean gioiTinh;
    private LocalDate ngaySinh;
    private String sdt;
    private String email;

    private String tenDangNhap;
    private String matKhau;
    private int idNhomQuyen;

    private Integer idBoPhan;
//    private Integer idTaiKhoan;
}
