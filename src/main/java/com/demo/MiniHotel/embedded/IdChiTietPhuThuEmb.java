package com.demo.MiniHotel.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdChiTietPhuThuEmb {
    private Integer idChiTietPhieuThue;
    private Integer idPhuThu;
}
