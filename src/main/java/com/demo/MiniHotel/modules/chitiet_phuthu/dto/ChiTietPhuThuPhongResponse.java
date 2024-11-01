package com.demo.MiniHotel.modules.chitiet_phuthu.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietPhuThuPhongResponse {
    Integer idChiTietPhieuThue;
    Integer idPhuThu;
    String noiDung;

    String maPhong;

    Integer soLuong;
    LocalDate ngayTao;
    Long donGia;

    Boolean daThanhToan;
}
