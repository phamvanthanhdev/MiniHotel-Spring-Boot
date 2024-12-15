package com.demo.MiniHotel.modules.chitiet_phuthu.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CapNhatChiTietPhuThuRequest {
    Integer idChiTietPhuThu;
    Integer idPhuThu;
    Integer idChiTietPhieuThue;
    Integer soLuong;
}
