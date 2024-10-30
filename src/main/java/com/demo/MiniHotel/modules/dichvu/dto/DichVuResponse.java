package com.demo.MiniHotel.modules.dichvu.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DichVuResponse {
    private int idDichVu;
    private String tenDichVu;
    private Long donGia;
}
