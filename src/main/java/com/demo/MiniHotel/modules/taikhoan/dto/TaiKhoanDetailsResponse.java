package com.demo.MiniHotel.modules.taikhoan.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaiKhoanDetailsResponse {
    int idTaiKhoan;
    String tenDangNhap;
    int idNhomQuyen;
    String tenNhomQuyen;
    String nguoiSoHuu;
    String doiTuong;
}
