package com.demo.MiniHotel.model;

import com.demo.MiniHotel.embedded.IdChiTietThayDoiGiaPhongEmb;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "chitiet_thaydoi_giaphong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietThayDoiGiaPhong {
    @JsonIgnore
    @EmbeddedId
    IdChiTietThayDoiGiaPhongEmb idChiTietThayDoiGiaPhongEmb;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idNhanVien")
    @JoinColumn(name = "idnhan_vien", nullable = false)
    NhanVien nhanVien;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idHangPhong")
    @JoinColumn(name = "idhang_phong", nullable = false)
    HangPhong hangPhong;

//    @Column(name = "ngay_cap_nhat", nullable = false, insertable = false, updatable = false)
//    LocalDate ngayCapNhat;

    @Column(name = "gia_cap_nhat", nullable = false)
    long giaCapNhat;

    @Column(name = "ngay_ap_dung", nullable = false)
    LocalDate ngayApDung;
}
