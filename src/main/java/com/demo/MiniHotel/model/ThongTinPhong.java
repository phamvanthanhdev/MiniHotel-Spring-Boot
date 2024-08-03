package com.demo.MiniHotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Table(name = "thongtin_phong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinPhong {
    @Id
    @Column(name = "ma_phong")
    private String maPhong;
    @Column(name = "tang")
    private Integer tang;
    @Column(name = "idhang_phong")
    private Integer idHangPhong;
    @Column(name = "ten_hang_phong", nullable = false)
    private String tenHangPhong;
    @Column(name = "so_nguoi_toi_da")
    private Integer soNguoiToiDa;
    @Column(name = "gia_goc")
    private Long giaGoc;
    @Column(name = "gia_khuyen_mai")
    private Long giaKhuyenMai;
    @Column(name = "ten_trang_thai")
    private String tenTrangThai;
}
