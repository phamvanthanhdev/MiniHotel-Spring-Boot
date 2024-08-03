package com.demo.MiniHotel.modules.hoadon.dto;

import com.demo.MiniHotel.model.NhanVien;
import com.demo.MiniHotel.model.PhieuThuePhong;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HoaDonRequest {
    private Integer idNhanVien;
    private Integer idPhieuThue;
    private Long tongTien;
    private LocalDate ngayTao;
}
