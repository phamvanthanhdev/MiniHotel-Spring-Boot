package com.demo.MiniHotel.modules.chitiet_phieudat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhieuDatResponse2 {
    private Integer soLuong;
    private Integer idHangPhong;
    private String tenHangPhong;
    private Long donGia;
    private Integer soLuongTrong;
}
