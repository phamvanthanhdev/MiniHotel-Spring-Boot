package com.demo.MiniHotel.modules.nhanvien.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhanVienResponse {
    private int idNhanVien;
    private String hoTen;
    private boolean gioiTinh;
    private LocalDate ngaySinh;
    private String sdt;
    private String email;
}
