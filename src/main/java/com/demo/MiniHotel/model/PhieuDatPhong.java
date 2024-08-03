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
@Table(name = "phieu_dat_phong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuDatPhong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idphieu_dat")
    private Integer idPhieuDat;
    @Column(name = "ngay_bat_dau", nullable = false)
    private LocalDate ngayBatDau;
    @Column(name = "ngay_tra_phong", nullable = false)
    private LocalDate ngayTraPhong;
    @Column(name = "ghi_chu")
    private String ghiChu;
    @Column(name = "ngay_tao", nullable = false)
    private LocalDate ngayTao;
    @Column(name = "tien_tam_ung")
    private Long tienTamUng;
    @Column(name = "trang_thai_huy", nullable = false)
    private Boolean trangThaiHuy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idkhach_hang", nullable = false)
    private KhachHang khachHang;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idnhan_vien")
    private NhanVien nhanVien;

    @JsonIgnore
    @OneToMany(mappedBy = "phieuDatPhong")
    private List<ChiTietPhieuDat> chiTietPhieuDats;

    @JsonIgnore
    @OneToOne(mappedBy = "phieuDatPhong")
    private PhieuThuePhong phieuThuePhong;
}
