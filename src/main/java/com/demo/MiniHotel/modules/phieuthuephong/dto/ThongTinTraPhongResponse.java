package com.demo.MiniHotel.modules.phieuthuephong.dto;

import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueResponse;
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
public class ThongTinTraPhongResponse {
    int idPhieuThue;
    LocalDate ngayNhanPhong;
    LocalDate ngayTraPhong;
    LocalDate ngayTao;
    String hoTenKhach;
    long tienTamUng;
    long tongTien;
    long tongTienPhong;
    int phanTramGiam;
    List<ChiTietPhieuThueResponse> chiTietPhieuThues;
    List<ChiTietSuDungDichVuResponse> chiTietDichVus;
    List<ChiTietPhuThuResponse> chiTietPhuThus;
}
