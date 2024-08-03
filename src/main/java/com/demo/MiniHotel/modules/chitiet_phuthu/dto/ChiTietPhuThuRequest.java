package com.demo.MiniHotel.modules.chitiet_phuthu.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChiTietPhuThuRequest {
    private Integer idPhuThu;
    private Integer idChiTietPhieuThue;
    private Integer soLuong;
    private String soHoaDon;
    private LocalDate ngayTao;
    private Long donGia;
    private Boolean daThanhToan;
}
