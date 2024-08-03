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
@Table(name = "phong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Phong {
    @Id
    @Column(name = "ma_phong", length = 50, unique = true)
    private String maPhong;
    @Column(name = "tang", nullable = false)
    private Integer tang;
    @Column(name = "mo_ta")
    private String moTa;
    @Column(name = "ngay_tao", nullable = false)
    private LocalDate ngayTao;
    @Column(name = "ngay_cap_nhat", nullable = false)
    private LocalDate ngayCapNhat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idhang_phong", referencedColumnName = "idhang_phong", nullable = false)
    private HangPhong hangPhong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idtrang_thai", nullable = false)
    private TrangThai trangThai;

    @JsonIgnore
    @OneToMany(mappedBy = "phong")
    private List<ChiTietPhieuThue> chiTietPhieuThues;
}
