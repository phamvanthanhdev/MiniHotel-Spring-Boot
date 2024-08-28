package com.demo.MiniHotel.model;

import com.demo.MiniHotel.embedded.IdChiTietKhachThueEmb;
import com.demo.MiniHotel.embedded.IdChiTietPhieuDatEmb;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
//@Table(name = "chitiet_khachthue")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class ChiTietKhachThue {
    @JsonIgnore
    @EmbeddedId
    private IdChiTietKhachThueEmb idChiTietKhachThueEmb;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idChiTietPhieuThue")
    @JoinColumn(name = "idchitiet_phieuthue", nullable = false)
    private ChiTietPhieuThue chiTietPhieuThue;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idKhachHang")
    @JoinColumn(name = "idkhach_hang", nullable = false)
    private KhachHang khachHang;
}
