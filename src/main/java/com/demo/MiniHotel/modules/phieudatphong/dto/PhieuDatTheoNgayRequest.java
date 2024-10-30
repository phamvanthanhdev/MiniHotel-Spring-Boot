package com.demo.MiniHotel.modules.phieudatphong.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhieuDatTheoNgayRequest {
    LocalDate ngay;
    int trangThai;
}
