package com.demo.MiniHotel.modules.phieuthuephong.dto;

import com.demo.MiniHotel.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuThueResponse {
    private Integer idPhieuThue;
    private LocalDate ngayDen;
    private LocalDate ngayDi;
    private LocalDate ngayTao;
    private String hoTenKhach;
    private String cmnd;
    private Integer idNhanVien;
    private String tenNhanVien;
    private Integer idPhieuDat;


    private Long tienTamUng;
    private long tongTien;
    private long tongTienPhong;
    private long tongTienDichVu;
    private long tongTienPhuThu;
    private long tienDuocGiam;
    private long tienPhaiTra;

    private int phanTramGiam;
}
