package com.demo.MiniHotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "dich_vu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DichVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddich_vu")
    private Integer idDichVu;
    @Column(name = "ten_dich_vu", nullable = false)
    private String tenDichVu;
    @Column(name = "don_gia", nullable = false)
    private Long donGia;

    @JsonIgnore
    @OneToMany(mappedBy = "dichVu")
    private List<ChiTietSuDungDichVu> chiTietSuDungDichVus;
}
