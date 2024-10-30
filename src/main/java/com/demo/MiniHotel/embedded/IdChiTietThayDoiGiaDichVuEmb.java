package com.demo.MiniHotel.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdChiTietThayDoiGiaDichVuEmb {
    private Integer idDichVu;
    private Integer idNhanVien;
    private LocalDate ngayCapNhat;
}
