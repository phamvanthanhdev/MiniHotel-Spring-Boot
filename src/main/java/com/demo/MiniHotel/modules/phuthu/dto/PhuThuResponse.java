package com.demo.MiniHotel.modules.phuthu.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhuThuResponse {
    int idPhuThu;
    String noiDung;
    Long donGia;
}
