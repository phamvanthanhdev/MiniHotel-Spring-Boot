package com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CapNhatChiTietSuDungDichVuRequest {
    Integer idChiTietSuDungDichVu;
    Integer idDichVu;
    Integer idChiTietPhieuThue;
    Integer soLuong;
}
