package com.demo.MiniHotel.modules.nhanvien.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienDangNhapResponse;
import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienDetailsResponse;
import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienRequest;
import com.demo.MiniHotel.model.NhanVien;
import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienResponse;
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
    public ResponseEntity<ApiResponse> addNewNhanVien(@RequestBody NhanVienRequest request) throws Exception {
        NhanVienDetailsResponse response = nhanVienService.addNewNhanVien(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllNhanVien(){
        List<NhanVienResponse> nhanViens = nhanVienService.getAllNhanVienResponse();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(nhanViens)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhanVien> getNhanVienById(@PathVariable("id") Integer id) throws Exception {
        NhanVien nhanVien = nhanVienService.getNhanVienById(id);
        return new ResponseEntity<>(nhanVien, HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ApiResponse> getNhanVienDetailsById(@PathVariable("id") Integer id) throws Exception {
        NhanVienDetailsResponse nhanVien = nhanVienService.getNhanVienDetailsById(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(nhanVien)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateNhanVien(@PathVariable("id") Integer id,
                                                     @RequestBody NhanVienRequest request) throws Exception {
        NhanVienDetailsResponse nhanVien = nhanVienService.updateNhanVien(request,id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(nhanVien)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteNhanVien(@PathVariable("id") Integer id) throws Exception {
        nhanVienService.deleteNhanVien(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/dang-nhap")
    public ResponseEntity<ApiResponse> getNhanVienByTenDangNhap() throws Exception {
        NhanVienDangNhapResponse nhanVien = nhanVienService.getThongTinNhanVienDangNhapByToken();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(nhanVien)
                .build()
                , HttpStatus.OK);
    }
}
