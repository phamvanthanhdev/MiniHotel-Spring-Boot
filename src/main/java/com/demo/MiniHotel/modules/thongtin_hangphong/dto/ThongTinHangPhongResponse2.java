package com.demo.MiniHotel.modules.thongtin_hangphong.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ThongTinHangPhongResponse2 {
    Integer idHangPhong;
    String tenHangPhong;
    String tenKieuPhong;
    String tenLoaiPhong;
    Integer soNguoiToiDa;
    Integer soLuongTrong;
    Long giaGoc;
    Long giaKhuyenMai;
    int soNgay;
    Long tongTien;
}
