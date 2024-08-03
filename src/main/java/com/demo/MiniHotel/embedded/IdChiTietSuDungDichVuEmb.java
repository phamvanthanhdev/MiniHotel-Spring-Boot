package com.demo.MiniHotel.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdChiTietSuDungDichVuEmb implements Serializable {
    private Integer idChiTietPhieuThue;
    private Integer idDichVu;
}
