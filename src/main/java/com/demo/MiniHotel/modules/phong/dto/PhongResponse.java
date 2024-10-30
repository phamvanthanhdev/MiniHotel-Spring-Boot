package com.demo.MiniHotel.modules.phong.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhongResponse {
    String maPhong;
    Integer tang;
    Integer idHangPhong;
    String tenHangPhong;
    Integer soNguoiToiDa;
    Long giaGoc;
    Long giaKhuyenMai;
    String tenTrangThai;
    Boolean daThue;
    LocalDate ngayTao;
    LocalDate ngayCapNhat;
}
