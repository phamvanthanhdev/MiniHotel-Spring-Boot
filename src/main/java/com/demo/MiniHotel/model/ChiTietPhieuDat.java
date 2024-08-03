package com.demo.MiniHotel.model;

import com.demo.MiniHotel.embedded.IdChiTietPhieuDatEmb;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chitiet_phieudat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhieuDat {
    @JsonIgnore
    @EmbeddedId
    private IdChiTietPhieuDatEmb idChiTietPhieuDatEmb;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idPhieuDat")
    @JoinColumn(name = "idphieu_dat", nullable = false)
    private PhieuDatPhong phieuDatPhong;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idHangPhong")
    @JoinColumn(name = "idhang_phong", nullable = false)
    private HangPhong hangPhong;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "don_gia", nullable = false)
    private Long donGia;
}
