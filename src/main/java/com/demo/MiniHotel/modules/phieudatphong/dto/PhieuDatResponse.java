package com.demo.MiniHotel.modules.phieudatphong.dto;

import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PhieuDatResponse {
    private Integer idPhieuDat;
    private LocalDate ngayBatDau;
    private LocalDate ngayTraPhong;
    private String ghiChu;
    private LocalDate ngayTao;
    private Long tienTamUng;
    private Integer idKhachHang;
    private Integer idNhanVien;
    private Boolean trangThaiHuy;
    //private List<ChiTietPhieuDatResponse> chiTietResponses;


    public PhieuDatResponse(Integer idPhieuDat, LocalDate ngayBatDau,
                            LocalDate ngayTraPhong, String ghiChu, LocalDate ngayTao, Long tienTamUng, Integer idKhachHang, Integer idNhanVien, Boolean trangThaiHuy) {
        this.idPhieuDat = idPhieuDat;
        this.ngayBatDau = ngayBatDau;
        this.ngayTraPhong = ngayTraPhong;
        this.ghiChu = ghiChu;
        this.ngayTao = ngayTao;
        this.tienTamUng = tienTamUng;
        this.idKhachHang = idKhachHang;
        this.idNhanVien = idNhanVien;
        this.trangThaiHuy = trangThaiHuy;
    }
}
