package com.demo.MiniHotel.modules.ctkm.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.ChuongTrinhKhuyenMai;
import com.demo.MiniHotel.modules.ctkm.dto.ChuongTrinhKhuyenMaiRequest;
import com.demo.MiniHotel.modules.ctkm.dto.ChuongTrinhKhuyenMaiResponse;
import com.demo.MiniHotel.modules.ctkm.service.IChuongTrinhKhuyenMaiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chuong-trinh-khuyen-mai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChuongTrinhKhuyenMaiController {
    IChuongTrinhKhuyenMaiService ChuongTrinhKhuyenMaiService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> themChuongTrinhKhuyenMai(@RequestBody ChuongTrinhKhuyenMaiRequest request) throws Exception {
        ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = ChuongTrinhKhuyenMaiService.themChuongTrinhKhuyenMai(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(chuongTrinhKhuyenMai)
                .build()
                , HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllChuongTrinhKhuyenMaiResponse(){
        List<ChuongTrinhKhuyenMaiResponse> responses = ChuongTrinhKhuyenMaiService.getAllChuongTrinhKhuyenMaiResponse();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getChuongTrinhKhuyenMaiResponseById(@PathVariable("id") Integer id) throws Exception {
        ChuongTrinhKhuyenMaiResponse response = ChuongTrinhKhuyenMaiService.getChuongTrinhKhuyenMaiResponseById(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateChuongTrinhKhuyenMai(@PathVariable("id") Integer id,
                                               @RequestBody ChuongTrinhKhuyenMaiRequest request) throws Exception {
        ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = ChuongTrinhKhuyenMaiService.updateChuongTrinhKhuyenMai(request,id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(chuongTrinhKhuyenMai)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteChuongTrinhKhuyenMai(@PathVariable("id") Integer id) throws Exception {
        ChuongTrinhKhuyenMaiService.deleteChuongTrinhKhuyenMai(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.OK);
    }
}
