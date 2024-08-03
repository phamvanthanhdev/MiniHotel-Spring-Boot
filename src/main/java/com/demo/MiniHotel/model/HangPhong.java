package com.demo.MiniHotel.model;

import com.demo.MiniHotel.embedded.IdHangPhongEmb;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.List;

@Entity
@Table(name = "hang_phong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HangPhong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhang_phong")
    private Integer idHangPhong;

    @Column(name = "ten_hang_phong", nullable = false)
    private String tenHangPhong;
    @Column(name = "mo_ta")
    private String moTa;
    @Column(name = "hinh_anh", nullable = false)
    @JsonIgnore
    @Lob
    private Blob hinhAnh;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idkieu_phong", nullable = false)
    private KieuPhong kieuPhong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idloai_phong", nullable = false)
    private LoaiPhong loaiPhong;

    @JsonIgnore
    @OneToMany(mappedBy = "hangPhong")
    private List<Phong> phongs;

    @JsonIgnore
    @OneToMany(mappedBy = "hangPhong")
    private List<ChiTietPhieuDat> chiTietPhieuDats;
}
