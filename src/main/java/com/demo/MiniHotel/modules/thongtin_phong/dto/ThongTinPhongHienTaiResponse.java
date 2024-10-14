package com.demo.MiniHotel.modules.thongtin_phong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinPhongHienTaiResponse {
    private String maPhong;
    private Integer tang;
    private Integer idHangPhong;
    private String tenHangPhong;
    private Integer soNguoiToiDa;
    private Long giaGoc;
    private Long giaKhuyenMai;
    private String tenTrangThai;
    private Boolean daThue;
    private Integer idChiTietPhieuThue;
    private Integer idPhieuThue;
    private LocalDate ngayDen;
    private LocalDate ngayDi;
}
