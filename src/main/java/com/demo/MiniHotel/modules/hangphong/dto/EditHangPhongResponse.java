package com.demo.MiniHotel.modules.hangphong.dto;

import com.demo.MiniHotel.model.KieuPhong;
import com.demo.MiniHotel.model.LoaiPhong;
import lombok.Data;

@Data
public class EditHangPhongResponse {
    private Integer id;
    private Integer idLoaiPhong;
    private Integer idKieuPhong;
    private String tenHangPhong;
    private String moTa;
    private String hinhAnh;

    public EditHangPhongResponse(Integer id, Integer idLoaiPhong,
                                 Integer idKieuPhong, String tenHangPhong,
                                 String moTa, String hinhAnh) {
        this.id = id;
        this.idLoaiPhong = idLoaiPhong;
        this.idKieuPhong = idKieuPhong;
        this.tenHangPhong = tenHangPhong;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
    }
}
