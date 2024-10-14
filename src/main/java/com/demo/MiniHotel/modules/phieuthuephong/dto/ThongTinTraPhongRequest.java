package com.demo.MiniHotel.modules.phieuthuephong.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongTinTraPhongRequest {
    int idPhieuThue;
    List<Integer> idChiTietPhieuThues;
}
