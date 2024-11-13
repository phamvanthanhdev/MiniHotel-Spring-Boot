package com.demo.MiniHotel.modules.chitiet_phieudat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietUserResponse {
    private Integer idChiTietPhieuDat;
    private  Integer idHangPhong;
    private String tenHangPhong;
    private Integer soLuong;
    private String hinhAnh;
    private Long donGia;
    private long tongTien;
}
