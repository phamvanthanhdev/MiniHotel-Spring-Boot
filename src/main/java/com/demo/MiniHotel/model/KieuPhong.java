package com.demo.MiniHotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "kieu_phong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KieuPhong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkieu_phong")
    private Integer idKieuPhong;
    @Column(name = "ten_kieu_phong", nullable = false, unique = true)
    private String tenKieuPhong;
    @Column(name = "mo_ta")
    private String moTa;

    @JsonIgnore
    @OneToMany(mappedBy = "kieuPhong")
    private List<HangPhong> hangPhongs;
}
