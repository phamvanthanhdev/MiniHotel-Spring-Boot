package com.demo.MiniHotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "loai_phong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoaiPhong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idloai_phong")
    private Integer idLoaiPhong;
    @Column(name = "ten_loai_phong", nullable = false, unique = true)
    private String tenLoaiPhong;
    @Column(name = "mo_ta")
    private String moTa;
    @Column(name = "so_nguoi_toi_da")
    private Integer soNguoiToiDa;

    @JsonIgnore
    @OneToMany(mappedBy = "loaiPhong")
    private List<HangPhong> hangPhongs;
}
