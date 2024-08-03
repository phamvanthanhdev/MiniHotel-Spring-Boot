package com.demo.MiniHotel.modules.hoadon.controller;

import com.demo.MiniHotel.model.ChiTietPhieuThue;
import com.demo.MiniHotel.model.ChiTietSuDungDichVu;
import com.demo.MiniHotel.model.HoaDon;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonRequest;
import com.demo.MiniHotel.modules.hoadon.service.IHoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hoa-don")
@RequiredArgsConstructor
public class HoaDonController {
    private final IHoaDonService HoaDonService;
    @PostMapping("/")
    public ResponseEntity<HoaDon> addNewHoaDon(@RequestBody HoaDonRequest request) throws Exception {
        HoaDon HoaDon = HoaDonService.addNewHoaDon(request);
        return new ResponseEntity<>(HoaDon, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HoaDon>> getAllHoaDon(){
        List<HoaDon> HoaDons = HoaDonService.getAllHoaDon();
        return new ResponseEntity<>(HoaDons, HttpStatus.OK);
    }

    @GetMapping("/{soHoaDon}")
    public ResponseEntity<HoaDon> getHoaDonById(@PathVariable("soHoaDon") String soHoaDon) throws Exception {
        HoaDon HoaDon = HoaDonService.getHoaDonById(soHoaDon);
        return new ResponseEntity<>(HoaDon, HttpStatus.OK);
    }

    @PutMapping("/{soHoaDon}")
    public ResponseEntity<HoaDon> updateHoaDon(@PathVariable("soHoaDon") String soHoaDon,
                                                     @RequestBody HoaDonRequest request) throws Exception {
        HoaDon HoaDon = HoaDonService.updateHoaDon(request,soHoaDon);
        return new ResponseEntity<>(HoaDon, HttpStatus.OK);
    }

    @DeleteMapping("/{soHoaDon}")
    public ResponseEntity<String> deleteHoaDon(@PathVariable("soHoaDon") String soHoaDon) throws Exception {
        HoaDonService.deleteHoaDon(soHoaDon);
        return new ResponseEntity<>("Deleted No." + soHoaDon + " successfully.", HttpStatus.OK);
    }

    @GetMapping("/chi-tiet-phieu-thue/{soHoaDon}")
    public ResponseEntity<List<ChiTietPhieuThue>> getChiTietPhieuThueBySoHoaDon(@PathVariable("soHoaDon") String soHoaDon) throws Exception {
        List<ChiTietPhieuThue>  chiTietPhieuThues = HoaDonService.getChiTietPhieuThueBySoHoaDon(soHoaDon);
        return new ResponseEntity<>(chiTietPhieuThues, HttpStatus.OK);
    }

    @GetMapping("/chi-tiet-dich-vu/{soHoaDon}")
    public ResponseEntity<List<ChiTietSuDungDichVu>> getChiTietDichVuBySoHoaDon(@PathVariable("soHoaDon") String soHoaDon) throws Exception {
        List<ChiTietSuDungDichVu>  chiTietSuDungDichVus = HoaDonService.getChiTietSuDungDichVuBySoHoaDon(soHoaDon);
        return new ResponseEntity<>(chiTietSuDungDichVus, HttpStatus.OK);
    }

}
