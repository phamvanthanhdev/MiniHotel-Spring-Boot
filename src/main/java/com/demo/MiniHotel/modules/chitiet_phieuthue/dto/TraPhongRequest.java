package com.demo.MiniHotel.modules.chitiet_phieuthue.dto;

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
public class TraPhongRequest {
    int phanTramGiam;
    Long tongThu;
    long thucThu;
    int idPhieuThue;
    long tienTamUng;
    List<Integer> idChiTietPhieuThues;
}
