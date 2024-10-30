package com.demo.MiniHotel.modules.hoadon.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoanhThuQuyResponse {
    int quy;
    long doanhThu;
}
