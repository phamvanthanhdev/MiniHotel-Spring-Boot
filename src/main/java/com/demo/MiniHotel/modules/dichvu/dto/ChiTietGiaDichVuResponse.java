package com.demo.MiniHotel.modules.dichvu.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietGiaDichVuResponse {
    int idDichVu;
    String tenDichVu;
    int idNhanVien;
    String tenNhanVien;
    long giaCapNhat;
    LocalDate ngayApDung;
    LocalDate ngayCapNhat;
}
