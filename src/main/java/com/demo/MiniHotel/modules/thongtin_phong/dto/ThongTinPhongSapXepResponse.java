package com.demo.MiniHotel.modules.thongtin_phong.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongTinPhongSapXepResponse {
    int idHangPhong;
    String tenHangPhong;
    List<ThongTinPhongHienTaiResponse> thongTinPhongs;
}
