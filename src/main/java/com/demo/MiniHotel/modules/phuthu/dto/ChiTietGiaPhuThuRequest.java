package com.demo.MiniHotel.modules.phuthu.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietGiaPhuThuRequest {
    int idPhuThu;
    long giaCapNhat;
    LocalDate ngayApDung;
}
