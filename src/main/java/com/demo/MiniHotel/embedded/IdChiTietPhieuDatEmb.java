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
public class IdChiTietPhieuDatEmb implements Serializable {
    private Integer idPhieuDat;
    private Integer idHangPhong;
}
