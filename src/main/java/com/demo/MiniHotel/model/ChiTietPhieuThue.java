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
@Table(name = "chitiet_phieuthue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhieuThue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idchitiet_phieuthue")
    private Integer idChiTietPhieuThue;

    @Column(name = "ngay_den", nullable = false)
    private LocalDate ngayDen;
    @Column(name = "ngay_di", nullable = false)
    private LocalDate ngayDi;
    @Column(name = "don_gia", nullable = false)
    private Long donGia;
    @Column(name = "da_thanh_toan", nullable = false)
    private Boolean daThanhToan;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idphieu_thue", nullable = false)
    private PhieuThuePhong phieuThuePhong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ma_phong", nullable = false)
    private Phong phong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "so_hoa_don")
    private HoaDon hoaDon;

    @ManyToMany
    @JoinTable(
            name = "chitiet_khachthue",
            joinColumns = @JoinColumn(name = "idchitiet_phieuthue"),
            inverseJoinColumns = @JoinColumn(name = "idkhach_hang")
    )
    private List<KhachHang> khachHangs;

    @JsonIgnore
    @OneToMany(mappedBy = "chiTietPhieuThue")
    private List<ChiTietSuDungDichVu> chiTietSuDungDichVus;

    @JsonIgnore
    @OneToMany(mappedBy = "chiTietPhieuThue")
    private List<ChiTietPhuThu> chiTietPhuThus;


}
