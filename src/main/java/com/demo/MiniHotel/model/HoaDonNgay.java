package com.demo.MiniHotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "hoadon_ngay")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonNgay {
    @Id
    @Column(name = "so_hoa_don")
    private String soHoaDon;
    @Column(name = "idphieu_thue", nullable = false)
    private Integer idPhieuThue;
    @Column(name = "ho_ten", nullable = false)
    private String tenNhanVien;
    @Column(name = "tong_tien", nullable = false)
    private Long tongTien;
    @Column(name = "ngay_tao", nullable = false)
    private LocalDate ngayTao;
    @Column(name = "tong_gia_phong", nullable = false)
    private Long tongGiaPhong;
    @Column(name = "tong_dich_vu", nullable = false)
    private Long tongDichVu;
    @Column(name = "tong_phu_thu", nullable = false)
    private Long tongPhuThu;
    @Column(name = "tien_tam_ung", nullable = false)
    private Long tienTamUng;
}
