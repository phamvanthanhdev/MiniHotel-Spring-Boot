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
public class IdChiTietThayDoiGiaPhongEmb {
    private Integer idHangPhong;
    private Integer idNhanVien;
    private LocalDate ngayCapNhat;
}
