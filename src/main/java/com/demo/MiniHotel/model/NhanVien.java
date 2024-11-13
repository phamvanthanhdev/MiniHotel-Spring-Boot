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
@Table(name = "nhan_vien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhanVien {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnhan_vien")
    private Integer idNhanVien;
    @Column(name = "cccd", nullable = false, unique = true)
    private String cccd;
    @Column(name = "ho_ten", nullable = false)
    private String hoTen;
    @Column(name = "gioi_tinh", nullable = false)
    private boolean gioiTinh;
    @Column(name = "ngay_sinh", nullable = false)
    private LocalDate ngaySinh;
    @Column(name = "sdt", nullable = false)
    private String sdt;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idbo_phan", nullable = false)
    private BoPhan boPhan;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idtai_khoan", nullable = false)
    private TaiKhoan taiKhoan;

    @JsonIgnore
    @OneToMany(mappedBy = "nhanVien")
    private List<PhieuDatPhong> phieuDatPhongs;

    @JsonIgnore
    @OneToMany(mappedBy = "nhanVien")
    private List<HoaDon> hoaDons;

    @JsonIgnore
    @OneToMany(mappedBy = "nhanVien")
    private List<ChuongTrinhKhuyenMai> chuongTrinhKhuyenMais;

    @JsonIgnore
    @OneToMany(mappedBy = "nhanVien")
    private List<ChiTietThayDoiGiaPhong> chiTietThayDoiGiaPhongs;

    @JsonIgnore
    @OneToMany(mappedBy = "nhanVien")
    private List<ChiTietThayDoiGiaDichVu> chiTietThayDoiGiaDichVus;

    @JsonIgnore
    @OneToMany(mappedBy = "nhanVien")
    private List<ChiTietThayDoiGiaPhuThu> chiTietThayDoiGiaPhuThus;
}
