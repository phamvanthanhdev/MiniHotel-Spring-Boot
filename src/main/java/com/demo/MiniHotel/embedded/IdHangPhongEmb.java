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
public class IdHangPhongEmb implements Serializable {
    private Integer idKieuPhong;
    private Integer idLoaiPhong;
}
