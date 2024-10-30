package com.demo.MiniHotel.model;

import com.demo.MiniHotel.embedded.IdChiTietKhuyenMaiEmb;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "chitiet_ctkm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietKhuyenMai {
    @JsonIgnore
    @EmbeddedId
    private IdChiTietKhuyenMaiEmb idChiTietKhuyenMaiEmb;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idKhuyenMai")
    @JoinColumn(name = "idkhuyen_mai", nullable = false)
    private ChuongTrinhKhuyenMai chuongTrinhKhuyenMai;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idHangPhong")
    @JoinColumn(name = "idhang_phong", nullable = false)
    private HangPhong hangPhong;

    @Column(name = "phan_tram_giam", nullable = false)
    private int phanTramGiam;
}
