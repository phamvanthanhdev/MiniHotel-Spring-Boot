package com.demo.MiniHotel.modules.taikhoan.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoanResponse {
    private int idTaiKhoan;
    private String tenDangNhap;
    private String matKhau;
    private int idNhomQuyen;
    private String tenNhomQuyen;
}
