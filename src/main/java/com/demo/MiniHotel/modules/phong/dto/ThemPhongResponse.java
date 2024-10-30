package com.demo.MiniHotel.modules.phong.dto;

import com.demo.MiniHotel.model.HangPhong;
import com.demo.MiniHotel.model.TrangThai;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThemPhongResponse {
    String maPhong;
    Integer tang;
    String moTa;
    LocalDate ngayTao;
    LocalDate ngayCapNhat;
    int idHangPhong;
    int idTrangThai;
}
