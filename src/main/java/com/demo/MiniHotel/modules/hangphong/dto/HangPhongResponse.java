package com.demo.MiniHotel.modules.hangphong.dto;

import com.demo.MiniHotel.model.KieuPhong;
import com.demo.MiniHotel.model.LoaiPhong;
import lombok.Data;

@Data
public class HangPhongResponse {
    private Integer id;
    private LoaiPhong loaiPhong;
    private KieuPhong kieuPhong;

    private String tenHangPhong;
    private String moTa;
    private String hinhAnh;

    public HangPhongResponse(Integer id, LoaiPhong loaiPhong, KieuPhong kieuPhong, String tenHangPhong, String moTa, String hinhAnh) {
        this.id = id;
        this.loaiPhong = loaiPhong;
        this.kieuPhong = kieuPhong;
        this.tenHangPhong = tenHangPhong;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
    }
}
