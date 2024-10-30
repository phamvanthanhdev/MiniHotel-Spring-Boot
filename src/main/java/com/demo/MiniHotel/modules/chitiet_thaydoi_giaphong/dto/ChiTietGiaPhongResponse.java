package com.demo.MiniHotel.modules.chitiet_thaydoi_giaphong.dto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietGiaPhongResponse {
    int idHangPhong;
    String tenHangPhong;
    int idNhanVien;
    String tenNhanVien;
    long giaCapNhat;
    LocalDate ngayApDung;
    LocalDate ngayCapNhat;
    boolean dangApDung;
}
