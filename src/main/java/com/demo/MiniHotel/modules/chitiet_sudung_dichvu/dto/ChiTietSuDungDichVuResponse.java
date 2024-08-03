package com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietSuDungDichVuResponse {
    private Integer idChiTietPhieuThue;
    private Integer idDichVu;
    private String tenDichVu;
    private Integer soLuong;
    private LocalDate ngayTao;
    private Long donGia;
    private Boolean daThanhToan;
}
