package com.demo.MiniHotel.modules.chitiet_phieudat.dto;

import lombok.Data;

@Data
public class ChiTietPhieuDatRequest {
    private Integer idHangPhong;
    private Integer idPhieuDat;
    private Integer soLuong;
    private Long donGia;
}
