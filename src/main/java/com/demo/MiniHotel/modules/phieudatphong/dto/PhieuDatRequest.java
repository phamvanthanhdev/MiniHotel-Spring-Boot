package com.demo.MiniHotel.modules.phieudatphong.dto;

import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.model.NhanVien;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PhieuDatRequest {
    private LocalDate ngayBatDau;
    private LocalDate ngayTraPhong;
    private String ghiChu;
    private LocalDate ngayTao;
    private Long tienTamUng;

    private Integer idKhachHang;
    private Integer idNhanVien;

    private List<ChiTietRequest> chiTietRequests;
}
