package com.demo.MiniHotel.modules.nhanvien.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhanVienDangNhapResponse {
    int idNhanVien;
    String hoTen;
    String tenNhomQuyen;
}
