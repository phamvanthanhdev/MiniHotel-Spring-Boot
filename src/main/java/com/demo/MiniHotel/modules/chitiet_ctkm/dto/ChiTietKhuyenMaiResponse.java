package com.demo.MiniHotel.modules.chitiet_ctkm.dto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietKhuyenMaiResponse {
    int idKhuyenMai;
    String moTaKhuyenMai;
    int idHangPhong;
    String tenHangPhong;
    int phanTramGiam;
    long tienGiam;
    long giaGoc;
    long giaKhuyenMai;
    String trangThai;
}
