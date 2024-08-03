package com.demo.MiniHotel.modules.phieuthuephong.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChiTietKhachThueRequest {
    private Integer idChiTietPhieuThue;
    private List<Integer> idKhachThues;
}
