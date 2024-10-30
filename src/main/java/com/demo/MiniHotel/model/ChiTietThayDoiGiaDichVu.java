package com.demo.MiniHotel.model;

import com.demo.MiniHotel.embedded.IdChiTietThayDoiGiaDichVuEmb;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "chitiet_thaydoi_giadichvu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietThayDoiGiaDichVu {
    @JsonIgnore
    @EmbeddedId
    IdChiTietThayDoiGiaDichVuEmb idChiTietThayDoiGiaDichVuEmb;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idDichVu")
    @JoinColumn(name = "iddich_vu", nullable = false)
    DichVu dichVu;

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
