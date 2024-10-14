package com.demo.MiniHotel.vnpay;

import com.demo.MiniHotel.modules.khachhang.dto.KhachHangRequest;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuDatThanhToanRequest {
    private KhachHangRequest khachHang;
    private PhieuDatRequest phieuDat;
}
