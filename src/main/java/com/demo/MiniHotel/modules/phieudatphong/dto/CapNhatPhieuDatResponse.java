package com.demo.MiniHotel.modules.phieudatphong.dto;

import com.demo.MiniHotel.modules.chitiet_phieudat.dto.CapNhatChiTietPhieuDatResponse;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietUserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CapNhatPhieuDatResponse {
    private Integer idPhieuDat;
    private LocalDate ngayBatDau;
    private LocalDate ngayTraPhong;
    private String ghiChu;
    private LocalDate ngayTao;
    private Long tienTamUng;
    private Integer idKhachHang;
    private String hoTen;
    private Integer idNhanVien;
    private Integer trangThaiHuy;
    private Long tienTra;
    private Long tongTien;
    private List<CapNhatChiTietPhieuDatResponse> chiTietResponses;
}
