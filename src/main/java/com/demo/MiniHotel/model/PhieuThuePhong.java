package com.demo.MiniHotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "phieu_thue_phong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuThuePhong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idphieu_thue")
    private Integer idPhieuThue;
    @Column(name = "ngay_den", nullable = false)
    private LocalDate ngayDen;
    @Column(name = "ngay_di", nullable = false)
    private LocalDate ngayDi;
    @Column(name = "ngay_tao", nullable = false)
    private LocalDate ngayTao;

    @Column(name = "phan_tram_giam", nullable = false)
    private int phanTramGiam;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idkhach_hang", nullable = false)
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idnhan_vien", nullable = false)
    private NhanVien nhanVien;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idphieu_dat")
    private PhieuDatPhong phieuDatPhong;

    @JsonIgnore
    @OneToMany(mappedBy = "phieuThuePhong")
    private List<ChiTietPhieuThue> chiTietPhieuThues;

    @JsonIgnore
    @OneToMany(mappedBy = "phieuThuePhong")
    private List<HoaDon> hoaDons;
}
