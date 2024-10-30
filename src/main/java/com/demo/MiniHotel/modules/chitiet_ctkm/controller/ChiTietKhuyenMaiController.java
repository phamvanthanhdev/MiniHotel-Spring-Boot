package com.demo.MiniHotel.modules.chitiet_ctkm.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.ChiTietKhuyenMai;
import com.demo.MiniHotel.modules.chitiet_ctkm.dto.ChiTietKhuyenMaiRequest;
import com.demo.MiniHotel.modules.chitiet_ctkm.dto.ChiTietKhuyenMaiResponse;
import com.demo.MiniHotel.modules.chitiet_ctkm.service.IChiTietKhuyenMaiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chi-tiet-khuyen-mai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChiTietKhuyenMaiController {
    IChiTietKhuyenMaiService ChiTietKhuyenMaiService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> themChiTietKhuyenMai(@RequestBody ChiTietKhuyenMaiRequest request) throws Exception {
        ChiTietKhuyenMai chiTietKhuyenMai = ChiTietKhuyenMaiService.themChiTietKhuyenMai(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(chiTietKhuyenMai)
                .build(), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllChiTietKhuyenMaiResponse(){
        List<ChiTietKhuyenMaiResponse> responses = ChiTietKhuyenMaiService.getAllChiTietKhuyenMaiResponse();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ApiResponse> getChiTietKhuyenMaiResponseById(@RequestParam int idKhuyenMai,
                                                                       @RequestParam int idHangPhong) throws Exception {
        ChiTietKhuyenMaiResponse response = ChiTietKhuyenMaiService.getChiTietKhuyenMaiResponseById(idKhuyenMai, idHangPhong);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update-by-id")
    public ResponseEntity<ApiResponse> updateChiTietKhuyenMai(@RequestParam int idKhuyenMai,
                                                                   @RequestParam int idHangPhong,
                                               @RequestBody ChiTietKhuyenMaiRequest request) throws Exception {
        ChiTietKhuyenMai chiTietKhuyenMai = ChiTietKhuyenMaiService.updateChiTietKhuyenMai(request,idKhuyenMai, idHangPhong);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(chiTietKhuyenMai)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/del-by-id")
    public ResponseEntity<ApiResponse> deleteChiTietKhuyenMai(@RequestParam int idKhuyenMai,
                                                         @RequestParam int idHangPhong) throws Exception {
        ChiTietKhuyenMaiService.deleteChiTietKhuyenMai(idKhuyenMai, idHangPhong);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/ctkm/{idKhuyenMai}")
    public ResponseEntity<ApiResponse> getChiTietKhuyenMaiByIdKhuyenMai(@PathVariable int idKhuyenMai) throws Exception {
        List<ChiTietKhuyenMaiResponse> responses = ChiTietKhuyenMaiService.getChiTietKhuyenMaiByIdKhuyenMai(idKhuyenMai);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }
}
