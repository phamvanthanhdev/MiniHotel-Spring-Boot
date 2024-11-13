package com.demo.MiniHotel.modules.trangthai.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.TrangThai;
import com.demo.MiniHotel.modules.trangthai.dto.TrangThaiRequest;
import com.demo.MiniHotel.modules.trangthai.service.ITrangThaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trang-thai")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrangThaiController {
    private final ITrangThaiService TrangThaiService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> addNewTrangThai(@RequestBody TrangThaiRequest request) throws Exception {
        TrangThai trangThai = TrangThaiService.addNewTrangThai(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(trangThai)
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TrangThai>> getAllTrangThai(){
        List<TrangThai> TrangThais = TrangThaiService.getAllTrangThai();
        return new ResponseEntity<>(TrangThais, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getTrangThaiById(@PathVariable("id") Integer id) throws Exception {
        TrangThai trangThai = TrangThaiService.getTrangThaiById(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(trangThai)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTrangThai(@PathVariable("id") Integer id,
                                                     @RequestBody TrangThaiRequest request) throws Exception {
        TrangThai trangThai = TrangThaiService.updateTrangThai(request,id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(trangThai)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTrangThai(@PathVariable("id") Integer id) throws Exception {
        TrangThaiService.deleteTrangThai(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.OK);
    }
}
