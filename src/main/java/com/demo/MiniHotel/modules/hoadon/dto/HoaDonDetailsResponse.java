package com.demo.MiniHotel.modules.hoadon.dto;

import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueResponse;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuResponse;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDonDetailsResponse {
    String soHoaDon;
    LocalDate ngayTao;
    int idPhieuThue;
    String tenNhanVien;
    LocalDate ngayNhanPhong;
    LocalDate ngayTraPhong;
    String hoTenKhach;
//    long tienTamUng;
    long thucThu;
    long tongThu;
    List<ChiTietPhieuThueResponse> chiTietPhieuThues;
    List<ChiTietSuDungDichVuResponse> chiTietDichVus;
    List<ChiTietPhuThuResponse> chiTietPhuThus;
}
