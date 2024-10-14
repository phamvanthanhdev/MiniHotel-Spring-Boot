package com.demo.MiniHotel.modules.khachhang.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangRequest {
    @Size(min = 12, max = 12, message = "CCCD_INVALID")
    private String cmnd;
    @NotNull(message = "NAME_INVALID")
    @NotBlank(message = "NAME_INVALID")
    private String hoTen;
    @Size(min = 10, message = "SDT_INVALID")
    private String sdt;
    @NotNull(message = "DIACHI_INVALID")
    @NotBlank(message = "DIACHI_INVALID")
    private String diaChi;
    @Email(message = "EMAIL_INVALID")
    private String email;
    @NotNull(message = "GIOITINH_INVALID")
    private boolean gioiTinh;
    @NotNull(message = "NGAYSINH_INVALID")
    private LocalDate ngaySinh;

    private Integer idTaiKhoan;
}
