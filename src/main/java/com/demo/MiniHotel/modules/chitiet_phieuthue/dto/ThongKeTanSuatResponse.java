package com.demo.MiniHotel.modules.chitiet_phieuthue.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongKeTanSuatResponse {
    int idHangPhong;
    String tenHangPhong;
    int tanSuat;
    String tiLe;
    long soNgayThongKe;
    List<TanSuatThuePhongResponse> tanSuatThuePhongs;
}
