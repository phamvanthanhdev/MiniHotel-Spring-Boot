package com.demo.MiniHotel.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdChiTietKhuyenMaiEmb {
    private Integer idKhuyenMai;
    private Integer idHangPhong;
}
