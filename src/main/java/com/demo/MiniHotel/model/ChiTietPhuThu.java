package com.demo.MiniHotel.model;

import com.demo.MiniHotel.embedded.IdChiTietPhuThuEmb;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "chitiet_phuthu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhuThu {
    @JsonIgnore
    @EmbeddedId
    private IdChiTietPhuThuEmb idChiTietPhuThuEmb;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idChiTietPhieuThue")
    @JoinColumn(name = "idchitiet_phieuthue", nullable = false)
    private ChiTietPhieuThue chiTietPhieuThue;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idPhuThu")
    @JoinColumn(name = "idphu_thu", nullable = false)
    private PhuThu phuThu;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDate ngayTao;
    @Column(name = "don_gia", nullable = false)
    private Long donGia;
    @Column(name = "da_thanh_toan", nullable = false)
    private Boolean daThanhToan;
    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "so_hoa_don")
    private HoaDon hoaDon;
}
