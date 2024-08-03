package com.demo.MiniHotel.modules.hoadon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonResponse {
    private String soHoaDon;
    private String tenNhanVien;
    private Long tongTien;
    private LocalDate ngayTao;
}
