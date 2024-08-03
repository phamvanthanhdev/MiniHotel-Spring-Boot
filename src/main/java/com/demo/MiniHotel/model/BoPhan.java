package com.demo.MiniHotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "bo_phan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoPhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbo_phan")
    private Integer idBoPhan;
    @Column(name = "ten_bo_phan", nullable = false)
    private String tenBoPhan;

    @JsonIgnore
    @OneToMany(mappedBy = "boPhan")
    private List<NhanVien> nhanViens;
}
