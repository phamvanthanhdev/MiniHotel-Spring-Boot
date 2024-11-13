package com.demo.MiniHotel.modules.phieuthuephong.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhieuThueFilterRequest {
    int luaChon;
    LocalDate ngayBatDauLoc;
    LocalDate ngayKetThucLoc;
    String tenKhachHang;
    String noiDung;
}
