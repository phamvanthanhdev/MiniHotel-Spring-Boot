package com.demo.MiniHotel.modules.chitiet_phieudat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapNhatChiTietPhieuDatResponse {
    private Integer idChiTietPhieuDat;
    private Integer idPhieuDat;
    private  Integer idHangPhong;
    private String tenHangPhong;
    private Integer soLuong;
    private Long donGia;
    private long tongTien;
    private long soNgayThue;
    private int soLuongTrong;
}
