package com.demo.MiniHotel.modules.chitiet_ctkm.dto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietKhuyenMaiRequest {
    int idKhuyenMai;
    int idHangPhong;
    int phanTramGiam;
}
