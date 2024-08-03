package com.demo.MiniHotel.modules.chitiet_phieudat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhieuDatResponse {
//    private Integer idPhieuDat;
//    private Integer idHangPhong;
    private Integer soLuong;
    private String tenHangPhong;
//    private String hinhAnh;
    private Long donGia;


}
