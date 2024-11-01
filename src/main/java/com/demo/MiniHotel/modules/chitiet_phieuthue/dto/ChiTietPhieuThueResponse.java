package com.demo.MiniHotel.modules.chitiet_phieuthue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietPhieuThueResponse {
    private Integer idChiTietPhieuThue;
    private String maPhong;
    private Integer idHangPhong;
    private String tenHangPhong;
    private Integer idPhieuThue;
    private LocalDate ngayDen;
    private LocalDate ngayDi;
    private Long donGia;
    private Boolean daThanhToan;
    private Long tienGiamGia;
    private Long tongTienPhong;
    private Long tongTienDichVu;
    private Long tongTienPhuThu;
    private Long tongTienTatCa;
}
