package com.demo.MiniHotel.modules.chitiet_phieudat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapNhatChiTietPhieuDatRequest {
    private Integer idChiTietPhieuDat;
    private Integer idPhieuDat;
    private  Integer idHangPhong;
    private Integer soLuong;
    private Long donGia;
}
