package com.demo.MiniHotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "thongtin_dichvu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongTinDichVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddich_vu")
    int idDichVu;
    @Column(name = "ten_dich_vu")
    String tenDichVu;
    @Column(name = "gia_cap_nhat")
    long giaCapNhat;
    @Column(name = "ngay_ap_dung")
    LocalDate ngayApDung;
    @Column(name = "ngay_cap_nhat")
    LocalDate ngayCapNhat;
}
