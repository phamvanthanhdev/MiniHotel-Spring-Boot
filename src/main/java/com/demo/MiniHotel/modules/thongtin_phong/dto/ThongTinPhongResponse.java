package com.demo.MiniHotel.modules.thongtin_phong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinPhongResponse {
    private String maPhong;
    private Integer tang;
    private Integer idHangPhong;
    private String tenHangPhong;
    private Integer soNguoiToiDa;
    private Long giaGoc;
    private Long giaKhuyenMai;
    private String tenTrangThai;
    private Boolean daThue;
}
