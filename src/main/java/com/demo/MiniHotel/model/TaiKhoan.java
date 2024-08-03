package com.demo.MiniHotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tai_khoan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtai_khoan")
    private Integer idTaiKhoan;
    @Column(name = "ten_dang_nhap", nullable = false)
    private String tenDangNhap;
    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

//    @JsonIgnore
    @OneToOne(mappedBy = "taiKhoan")
    private NhanVien nhanVien;

    @OneToOne(mappedBy = "taiKhoan")
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idnhom_quyen", nullable = false)
    private NhomQuyen nhomQuyen;
}
