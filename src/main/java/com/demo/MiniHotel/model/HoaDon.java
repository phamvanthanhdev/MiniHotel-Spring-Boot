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
@Table(name = "hoa_don")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "so_hoa_don")
    private String soHoaDon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idnhan_vien", nullable = false)
    private NhanVien nhanVien;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idphieu_thue")
    private PhieuThuePhong phieuThuePhong;

    @Column(name = "tong_tien", nullable = false)
    private Long tongTien;
    @Column(name = "ngay_tao", nullable = false)
    private LocalDate ngayTao;

//    @Column(name = "phan_tram_giam", nullable = false)
//    private int phanTramGiam;

    @JsonIgnore
    @OneToMany(mappedBy = "hoaDon")
    private List<ChiTietPhieuThue> chiTietPhieuThues;

    @JsonIgnore
    @OneToMany(mappedBy = "hoaDon")
    private List<ChiTietSuDungDichVu> chiTietSuDungDichVus;

    @JsonIgnore
    @OneToMany(mappedBy = "hoaDon")
    private List<ChiTietPhuThu> chiTietPhuThus;

    //Thiếu: Chi tiết phụ thu
}
