package com.demo.MiniHotel.modules.phieudatphong.dto;

import com.demo.MiniHotel.modules.khachhang.dto.KhachHangRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CapNhatKhachHangResquest {
    private int idPhieuDat;
    private List<KhachHangRequest> khachHangs;
}
