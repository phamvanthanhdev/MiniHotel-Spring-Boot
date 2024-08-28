package com.demo.MiniHotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "doanhthu_ngay")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoanhThuTheoNgay {
    @Id
    @Column(name = "ngay_tao")
    private LocalDate ngayTao;
    @Column(name = "tong_tien", nullable = false)
    private Long tongTien;
}
