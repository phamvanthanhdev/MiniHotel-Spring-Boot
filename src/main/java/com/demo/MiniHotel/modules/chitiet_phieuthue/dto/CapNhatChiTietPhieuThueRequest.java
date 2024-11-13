package com.demo.MiniHotel.modules.chitiet_phieuthue.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CapNhatChiTietPhieuThueRequest {
    int idChiTietPhieuThue;
    LocalDate ngayTraPhong;
    long tienGiamGia;
}
