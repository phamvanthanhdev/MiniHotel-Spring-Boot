package com.demo.MiniHotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "chuongtrinh_khuyenmai")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChuongTrinhKhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkhuyen_mai")
    private Integer idKhuyenMai;
    @Column(name = "mo_ta", nullable = false, length = 512)
    @Lob
    private String moTa;
    @Column(name = "ngay_bat_dau", nullable = false)
    private LocalDate ngayBatDau;
    @Column(name = "ngay_ket_thuc", nullable = false)
    private LocalDate ngayKetThuc;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idnhan_vien", nullable = false)
    private NhanVien nhanVien;

    @JsonIgnore
    @OneToMany(mappedBy = "chuongTrinhKhuyenMai")
    private List<ChiTietKhuyenMai> chiTietKhuyenMais;
}
