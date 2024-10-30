package com.demo.MiniHotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "doanhthu_thang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoanhThuTheoThang {
    @Id
    @Column(name = "thang")
    int thang;
    @Column(name = "doanhThu", nullable = false)
    long doanhThu;
}
