package com.demo.MiniHotel.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinHangPhongEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_idhang_phong")
    private Integer idHangPhong;
    @Column(name = "t_ten_hang_phong", nullable = false)
    private String tenHangPhong;
    @Column(name = "t_mo_ta")
    private String moTa;
//    @Column(name = "t_hinh_anh", nullable = false)
//    @Lob
//    private Blob hinhAnh;
    @Column(name = "t_ten_kieu_phong", nullable = false)
    private String tenKieuPhong;
    @Column(name = "t_ten_loai_phong", nullable = false)
    private String tenLoaiPhong;
    @Column(name = "t_so_nguoi_toi_da")
    private Integer soNguoiToiDa;
    @Column(name = "t_phan_tram_giam")
    private Float phanTramGiam;
    @Column(name = "t_gia_goc")
    private Long giaGoc;
    @Column(name = "t_gia_khuyen_mai")
    private Long giaKhuyenMai;
    @Column(name = "allotment")
    private Integer soLuongTrong;
}
