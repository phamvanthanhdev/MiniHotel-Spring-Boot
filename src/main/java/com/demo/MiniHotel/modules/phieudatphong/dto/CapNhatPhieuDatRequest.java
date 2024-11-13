package com.demo.MiniHotel.modules.phieudatphong.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CapNhatPhieuDatRequest {
    int idPhieuDat;
    LocalDate ngayTraPhong;
    LocalDate ngayNhanPhong;
    Long tienTra;
}
