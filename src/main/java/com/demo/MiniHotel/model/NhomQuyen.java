package com.demo.MiniHotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "nhom_quyen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhomQuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnhom_quyen")
    private Integer idNhomQuyen;
    @Column(name = "ten_nhom_quyen", nullable = false)
    private String tenNhomQuyen;

    @JsonIgnore
    @OneToMany(mappedBy = "nhomQuyen")
    private List<TaiKhoan> taiKhoans;
}
