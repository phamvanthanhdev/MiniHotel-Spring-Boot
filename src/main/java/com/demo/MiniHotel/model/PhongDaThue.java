package com.demo.MiniHotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "phong_da_thue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhongDaThue {
    @Id
    @Column(name = "ma_phong")
    private String maPhong;
    @Column(name = "idhang_phong", nullable = false)
    private Integer idHangPhong;
    @Column(name = "da_thue", nullable = false)
    private Boolean daThue;

}
