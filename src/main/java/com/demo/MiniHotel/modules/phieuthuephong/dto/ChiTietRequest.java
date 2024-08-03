package com.demo.MiniHotel.modules.phieuthuephong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietRequest {
    private String maPhong;
    private Integer idHangPhong;
    private LocalDate ngayDen;
    private LocalDate ngayDi;
    private Long donGia;
}
