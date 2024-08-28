package com.demo.MiniHotel.modules.thongtin_hangphong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinHangPhongAdminResponse {
    private Integer idHangPhong;
    private String tenHangPhong;
    private Integer soLuongTrong;
    private Long giaGoc;
    private Long giaKhuyenMai;
}
