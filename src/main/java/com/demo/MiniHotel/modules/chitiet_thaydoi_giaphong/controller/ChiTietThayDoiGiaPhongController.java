package com.demo.MiniHotel.modules.chitiet_thaydoi_giaphong.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.ChiTietThayDoiGiaPhong;
import com.demo.MiniHotel.modules.chitiet_thaydoi_giaphong.dto.ChiTietGiaPhongRequest;
import com.demo.MiniHotel.modules.chitiet_thaydoi_giaphong.dto.ChiTietGiaPhongResponse;
import com.demo.MiniHotel.modules.chitiet_thaydoi_giaphong.service.IChiTietThayDoiGiaPhongService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/chi-tiet-gia-phong")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChiTietThayDoiGiaPhongController {
    IChiTietThayDoiGiaPhongService chiTietThayDoiGiaPhongService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> themChiTietThayDoiGiaPhong(@RequestBody ChiTietGiaPhongRequest request) throws Exception {
        ChiTietThayDoiGiaPhong ChiTietThayDoiGiaPhong = chiTietThayDoiGiaPhongService.themChiTietThayDoiGiaPhong(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(ChiTietThayDoiGiaPhong)
                .build(), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllChiTietGiaPhongResponse(){
        List<ChiTietGiaPhongResponse> responses = chiTietThayDoiGiaPhongService.getAllChiTietThayDoiGiaPhongResponse();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ApiResponse> getChiTietGiaPhongResponseById(@RequestParam int idHangPhong,
                                                                       @RequestParam int idNhanVien,
                                                                      @RequestParam LocalDate ngayCapNhat) throws Exception {
        ChiTietGiaPhongResponse response = chiTietThayDoiGiaPhongService
                .getChiTietThayDoiGiaPhongResponseById(idHangPhong, idNhanVien, ngayCapNhat);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update-by-id")
    public ResponseEntity<ApiResponse> updateChiTietThayDoiGiaPhong(@RequestParam int idNhanVien,
                                                                   @RequestParam int idHangPhong,
                                                                    @RequestParam LocalDate ngayCapNhat,
                                               @RequestBody ChiTietGiaPhongRequest request) throws Exception {
        ChiTietGiaPhongResponse chiTietGiaPhongResponse = chiTietThayDoiGiaPhongService.updateChiTietThayDoiGiaPhong(request,idHangPhong, idNhanVien, ngayCapNhat);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(chiTietGiaPhongResponse)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/del-by-id")
    public ResponseEntity<ApiResponse> deleteChiTietThayDoiGiaPhong(@RequestParam int idNhanVien,
                                                         @RequestParam int idHangPhong, @RequestParam LocalDate ngayCapNhat) throws Exception {
        chiTietThayDoiGiaPhongService.deleteChiTietThayDoiGiaPhong(idHangPhong, idNhanVien, ngayCapNhat);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.OK);
    }
}
