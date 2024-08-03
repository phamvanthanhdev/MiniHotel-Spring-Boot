package com.demo.MiniHotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "khach_hang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkhach_hang")
    private Integer idKhachHang;
    @Column(name = "cmnd", nullable = false, unique = true)
    private String cmnd;
    @Column(name = "ho_ten", nullable = false)
    private String hoTen;
    @Column(name = "sdt", nullable = false)
    private String sdt;
    @Column(name = "dia_chi")
    private String diaChi;
    @Column(name = "ma_so_thue", unique = true)
    private String maSoThue;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idtai_khoan")
    private TaiKhoan taiKhoan;

    @JsonIgnore
    @OneToMany(mappedBy = "khachHang")
    private List<PhieuDatPhong> phieuDatPhongs;

    @JsonIgnore
    @OneToMany(mappedBy = "khachHang")
    private List<PhieuThuePhong> phieuThuePhongs;

    @JsonIgnore
    @ManyToMany(mappedBy = "khachHangs")
    private List<ChiTietPhieuThue> chiTietPhieuThues;
}
