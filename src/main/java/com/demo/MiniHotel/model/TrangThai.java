package com.demo.MiniHotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "trang_thai")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrangThai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtrang_thai")
    private Integer idTrangThai;
    @Column(name = "ten_trang_thai", nullable = false)
    private String tenTrangThai;

    @JsonIgnore
    @OneToMany(mappedBy = "trangThai")
    private List<Phong> phongs;
}
