package com.demo.MiniHotel.modules.phieudatphong.dto;

import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuDatDetailsResponse {
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
    private Long tongTien;
    private List<ChiTietUserResponse> chiTietResponses;
}
