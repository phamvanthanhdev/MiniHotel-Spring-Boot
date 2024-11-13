package com.demo.MiniHotel.modules.nhanvien.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhanVienDetailsResponse {
    int idNhanVien;
    String cccd;
    String hoTen;
    boolean gioiTinh;
    LocalDate ngaySinh;
    String sdt;
    String email;
    int idBoPhan;
    String tenBoPhan;
    int idTaiKhoan;
    String tenDangNhap;
}
