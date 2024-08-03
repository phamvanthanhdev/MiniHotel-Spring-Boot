package com.demo.MiniHotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Table(name = "thongtin_hangphong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinHangPhong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhang_phong")
    private Integer idHangPhong;
    @Column(name = "ten_hang_phong", nullable = false)
    private String tenHangPhong;
    @Column(name = "mo_ta")
    private String moTa;
    @Column(name = "hinh_anh", nullable = false)
    @Lob
    private Blob hinhAnh;
    @Column(name = "ten_kieu_phong", nullable = false)
    private String tenKieuPhong;
    @Column(name = "ten_loai_phong", nullable = false)
    private String tenLoaiPhong;
    @Column(name = "so_nguoi_toi_da")
    private Integer soNguoiToiDa;
    @Column(name = "phan_tram_giam")
    private Float phanTramGiam;
    @Column(name = "gia_goc")
    private Long giaGoc;
    @Column(name = "gia_khuyen_mai")
    private Long giaKhuyenMai;
}
