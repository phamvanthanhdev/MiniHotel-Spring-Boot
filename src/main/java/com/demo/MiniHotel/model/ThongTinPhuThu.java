package com.demo.MiniHotel.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "thongtin_phuthu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongTinPhuThu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idphu_thu")
    int idPhuThu;
    @Column(name = "noi_dung")
    String noiDung;
    @Column(name = "gia_cap_nhat")
    long giaCapNhat;
    @Column(name = "ngay_ap_dung")
    LocalDate ngayApDung;
    @Column(name = "ngay_cap_nhat")
    LocalDate ngayCapNhat;
}
