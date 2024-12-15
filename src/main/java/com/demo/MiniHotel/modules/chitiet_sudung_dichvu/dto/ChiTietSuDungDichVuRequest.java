package com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChiTietSuDungDichVuRequest {
    private Integer idChiTietSuDungDichVu;
    private Integer idDichVu;
    private Integer idChiTietPhieuThue;
    private Integer soLuong;
//    private String soHoaDon;
//    private LocalDate ngayTao;
    private Long donGia;
    private Boolean daThanhToan;
}
