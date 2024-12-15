package com.demo.MiniHotel.model;

import com.demo.MiniHotel.embedded.IdChiTietPhieuDatEmb;
import com.demo.MiniHotel.embedded.IdChiTietSuDungDichVuEmb;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "chitiet_sudung_dichvu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietSuDungDichVu {
//    @JsonIgnore
//    @EmbeddedId
//    private IdChiTietSuDungDichVuEmb idChiTietSuDungDichVuEmb;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.EAGER)
//    @MapsId("idChiTietPhieuThue")
//    @JoinColumn(name = "idchitiet_phieuthue", nullable = false)
//    private ChiTietPhieuThue chiTietPhieuThue;
//
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.EAGER)
//    @MapsId("idDichVu")
//    @JoinColumn(name = "iddich_vu", nullable = false)
//    private DichVu dichVu;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idchitiet_sudung_dichvu")
    private Integer idChiTietSuDungDichVu;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idchitiet_phieuthue", nullable = false)
    private ChiTietPhieuThue chiTietPhieuThue;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iddich_vu", nullable = false)
    private DichVu dichVu;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDate ngayTao;
    @Column(name = "don_gia", nullable = false)
    private Long donGia;
    @Column(name = "da_thanh_toan", nullable = false)
    private Boolean daThanhToan;
    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "so_hoa_don")
    private HoaDon hoaDon;
}
