package com.demo.MiniHotel.modules.khachhang.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhachHangUpload {
    String cccd;
    String hoTen;
    String gioiTinh;
    String ngaySinh;
    String sdt;
    String email;
    String diaChi;
}
