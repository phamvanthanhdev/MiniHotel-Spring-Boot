package com.demo.MiniHotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "phu_thu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhuThu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idphu_thu")
    private Integer idPhuThu;
    @Column(name = "noi_dung", nullable = false)
    private String noiDung;
    @Column(name = "don_gia", nullable = false)
    private Long donGia;

    @JsonIgnore
    @OneToMany(mappedBy = "phuThu")
    private List<ChiTietPhuThu> chiTietPhuThus;
}
