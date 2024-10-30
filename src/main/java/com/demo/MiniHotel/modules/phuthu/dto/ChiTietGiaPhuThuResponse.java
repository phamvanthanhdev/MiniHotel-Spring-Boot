package com.demo.MiniHotel.modules.phuthu.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietGiaPhuThuResponse {
    int idPhuThu;
    String noiDung;
    int idNhanVien;
    String tenNhanVien;
    long giaCapNhat;
    LocalDate ngayApDung;
    LocalDate ngayCapNhat;
}
