package com.demo.MiniHotel.modules.dichvu.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietGiaDichVuRequest {
    int idDichVu;
//    int idNhanVien;
    long giaCapNhat;
    LocalDate ngayApDung;
}
