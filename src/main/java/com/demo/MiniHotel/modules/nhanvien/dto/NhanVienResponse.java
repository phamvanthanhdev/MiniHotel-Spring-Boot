package com.demo.MiniHotel.modules.nhanvien.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVienResponse {
    private int idNhanVien;
    private String cccd;
    private String hoTen;
    private boolean gioiTinh;
    private LocalDate ngaySinh;
    private String sdt;
    private String email;
    private String tenBoPhan;
}
