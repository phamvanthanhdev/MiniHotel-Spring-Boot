package com.demo.MiniHotel.modules.nhanvien.controller;

import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienRequest;
import com.demo.MiniHotel.model.NhanVien;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhan-vien")
@RequiredArgsConstructor
public class NhanVienController {
    private final INhanVienService nhanVienService;
    @PostMapping("/")
    public ResponseEntity<NhanVien> addNewNhanVien(@RequestBody NhanVienRequest request) throws Exception {
        NhanVien nhanVien = nhanVienService.addNewNhanVien(request);
        return new ResponseEntity<>(nhanVien, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NhanVien>> getAllNhanVien(){
        List<NhanVien> nhanViens = nhanVienService.getAllNhanVien();
        return new ResponseEntity<>(nhanViens, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhanVien> getNhanVienById(@PathVariable("id") Integer id) throws Exception {
        NhanVien nhanVien = nhanVienService.getNhanVienById(id);
        return new ResponseEntity<>(nhanVien, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NhanVien> updateNhanVien(@PathVariable("id") Integer id,
                                                     @RequestBody NhanVienRequest request) throws Exception {
        NhanVien nhanVien = nhanVienService.updateNhanVien(request,id);
        return new ResponseEntity<>(nhanVien, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNhanVien(@PathVariable("id") Integer id) throws Exception {
        nhanVienService.deleteNhanVien(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }

    @GetMapping("/dang-nhap")
    public ResponseEntity<NhanVien> getNhanVienByTenDangNhap() throws Exception {
        NhanVien nhanVien = nhanVienService.getNhanVienByToken();
        return new ResponseEntity<>(nhanVien, HttpStatus.OK);
    }
}
