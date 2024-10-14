package com.demo.MiniHotel.modules.taikhoan.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoanResponse {
    private String tenDangNhap;
    private String matKhau;
    private String nhomQuyen;
}
