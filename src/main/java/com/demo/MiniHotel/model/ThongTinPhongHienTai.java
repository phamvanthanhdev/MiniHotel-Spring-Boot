package com.demo.MiniHotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "thongtin_phong_hientai")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinPhongHienTai {
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
    @Column(name = "da_thue")
    private Boolean daThue;
    @Column(name = "idchitiet_phieuthue")
    private Integer idchitiet_phieuthue;
    @Column(name = "ngay_den")
    private LocalDate ngayDen;
    @Column(name = "ngay_di")
    private LocalDate ngayDi;
}
