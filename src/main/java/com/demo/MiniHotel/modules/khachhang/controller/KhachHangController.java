package com.demo.MiniHotel.modules.khachhang.controller;

import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangRequest;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangResponse;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khach-hang")
@RequiredArgsConstructor
public class KhachHangController {
    private final IKhachHangService KhachHangService;
    @PostMapping("/")
    public ResponseEntity<KhachHang> addNewKhachHang(@RequestBody KhachHangRequest request) throws Exception {
        KhachHang KhachHang = KhachHangService.addNewKhachHang(request);
        return new ResponseEntity<>(KhachHang, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<KhachHang>> getAllKhachHang(){
        List<KhachHang> KhachHangs = KhachHangService.getAllKhachHang();
        return new ResponseEntity<>(KhachHangs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhachHang> getKhachHangById(@PathVariable("id") Integer id) throws Exception {
        KhachHang KhachHang = KhachHangService.getKhachHangById(id);
        return new ResponseEntity<>(KhachHang, HttpStatus.OK);
    }

    @GetMapping("/tim-kiem")
    public ResponseEntity<KhachHangResponse> getKhachHangBySdt(@RequestParam("sdt") String sdt) {
        KhachHangResponse khachHangResponse = null;
        try {
            khachHangResponse = KhachHangService.getKhachHangBySdt(sdt);
        } catch (Exception e) {
            return new ResponseEntity<>(new KhachHangResponse(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(khachHangResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhachHang> updateKhachHang(@PathVariable("id") Integer id,
                                                   @RequestBody KhachHangRequest request) throws Exception {
        KhachHang KhachHang = KhachHangService.updateKhachHang(request,id);
        return new ResponseEntity<>(KhachHang, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKhachHang(@PathVariable("id") Integer id) throws Exception {
        KhachHangService.deleteKhachHang(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }
}
