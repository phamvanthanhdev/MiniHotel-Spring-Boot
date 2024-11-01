package com.demo.MiniHotel.modules.chitiet_phieuthue.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TanSuatThuePhongResponse {
    String maPhong;
    int idHangPhong;
    int tanSuat;
    String tiLe;
}
