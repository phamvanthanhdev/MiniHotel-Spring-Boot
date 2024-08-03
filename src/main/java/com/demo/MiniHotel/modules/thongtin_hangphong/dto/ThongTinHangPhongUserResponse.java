package com.demo.MiniHotel.modules.thongtin_hangphong.dto;

import lombok.Data;

@Data
public class ThongTinHangPhongUserResponse {
    private Integer idHangPhong;
    private String tenHangPhong;
    private String moTa;
    private String hinhAnh;
    private String tenKieuPhong;
    private String tenLoaiPhong;
    private Float phanTramGiam;
    private Integer soNguoiToiDa;
    private Long giaGoc;
    private Long giaKhuyenMai;
    private Integer soLuongTrong;

    public ThongTinHangPhongUserResponse(Integer idHangPhong, String tenHangPhong, String moTa, String hinhAnh, String tenKieuPhong, String tenLoaiPhong, Float phanTramGiam,
                                         Integer soNguoiToiDa, Long giaGoc, Long giaKhuyenMai, Integer soLuongTrong) {
        this.idHangPhong = idHangPhong;
        this.tenHangPhong = tenHangPhong;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.tenKieuPhong = tenKieuPhong;
        this.tenLoaiPhong = tenLoaiPhong;
        this.phanTramGiam = phanTramGiam;
        this.soNguoiToiDa = soNguoiToiDa;
        this.giaGoc = giaGoc;
        this.giaKhuyenMai = giaKhuyenMai;
        this.soLuongTrong = soLuongTrong;
    }
}
