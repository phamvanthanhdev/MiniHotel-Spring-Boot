package com.demo.MiniHotel.modules.phieuthuephong.dto;

import com.demo.MiniHotel.model.PhieuDatPhong;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PhieuThuePhongRequest {
    private LocalDate ngayDen;
    private LocalDate ngayDi;
//    private boolean trangThai;
//    private LocalDate ngayTao;

    private Integer idKhachHang;
//    private Integer idNhanVien;
    private Integer idPhieuDat;

    private List<ChiTietRequest> chiTietRequests;
}
