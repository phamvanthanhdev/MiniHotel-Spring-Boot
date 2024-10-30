package com.demo.MiniHotel.modules.ctkm.dto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChuongTrinhKhuyenMaiResponse {
    int idKhuyenMai;
    String moTa;
    LocalDate ngayBatDau;
    LocalDate ngayKetThuc;
    String tenNhanVien;
}
