package com.demo.MiniHotel.modules.chitiet_phieuthue.dto;

import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuResponse;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraPhongResponse {
    int idPhieuThue;
    LocalDate ngayNhanPhong;
    LocalDate ngayTraPhong;
    String hoTenKhach;
    String hoTenNhanVien;
    long tienTamUng;
    int phanTramGiam;
    long tongThu;
    long thucThu;
    String soHoaDon;
    LocalDate ngayTaoHoaDon;
    List<ChiTietPhieuThueResponse> chiTietPhieuThues;
    List<ChiTietSuDungDichVuResponse> chiTietDichVus;
    List<ChiTietPhuThuResponse> chiTietPhuThus;
}
