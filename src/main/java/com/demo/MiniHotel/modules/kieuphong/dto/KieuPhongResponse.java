package com.demo.MiniHotel.modules.kieuphong.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KieuPhongResponse {
    String tenKieuPhong;
    String moTa;
    int soLuong;
}
