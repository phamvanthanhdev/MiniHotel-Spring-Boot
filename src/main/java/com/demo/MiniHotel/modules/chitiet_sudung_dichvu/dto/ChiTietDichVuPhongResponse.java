package com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietDichVuPhongResponse {
    Integer idChiTietPhieuThue;
    Integer idDichVu;
    String tenDichVu;

    String maPhong;

    Integer soLuong;
    LocalDate ngayTao;
    Long donGia;

    Boolean daThanhToan;
}
