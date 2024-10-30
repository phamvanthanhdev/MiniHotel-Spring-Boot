package com.demo.MiniHotel.model;

import com.demo.MiniHotel.embedded.IdChiTietThayDoiGiaDichVuEmb;
import com.demo.MiniHotel.embedded.IdChiTietThayDoiGiaPhuThuEmb;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "chitiet_thaydoi_giaphuthu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietThayDoiGiaPhuThu {
    @JsonIgnore
    @EmbeddedId
    IdChiTietThayDoiGiaPhuThuEmb idChiTietThayDoiGiaPhuThuEmb;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idPhuThu")
    @JoinColumn(name = "idphu_thu", nullable = false)
    PhuThu phuThu;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idNhanVien")
    @JoinColumn(name = "idnhan_vien", nullable = false)
    NhanVien nhanVien;

    @Column(name = "gia_cap_nhat", nullable = false)
    long giaCapNhat;

    @Column(name = "ngay_ap_dung", nullable = false)
    LocalDate ngayApDung;
}
