package com.demo.MiniHotel.modules.chitiet_phuthu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhuThuResponse {
    private Integer idChiTietPhieuThue;
    private Integer idPhuThu;
    private String noiDung;
    private Integer soLuong;
    private LocalDate ngayTao;
    private Long donGia;
    private Boolean daThanhToan;
}
