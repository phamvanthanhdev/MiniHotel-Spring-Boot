package com.demo.MiniHotel.modules.dichvu.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DichVuRequest {
    private String tenDichVu;
    private Long donGia;
}
