package com.demo.MiniHotel.modules.phuthu.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.ChiTietThayDoiGiaPhuThu;
import com.demo.MiniHotel.model.PhuThu;
import com.demo.MiniHotel.model.ThongTinPhuThu;
import com.demo.MiniHotel.modules.chitiet_phuthu.service.IChiTietPhuThuService;
import com.demo.MiniHotel.modules.dichvu.dto.DichVuResponse;
import com.demo.MiniHotel.modules.phuthu.dto.ChiTietGiaPhuThuRequest;
import com.demo.MiniHotel.modules.phuthu.dto.ChiTietGiaPhuThuResponse;
import com.demo.MiniHotel.modules.phuthu.dto.PhuThuRequest;
import com.demo.MiniHotel.modules.phuthu.dto.PhuThuResponse;
import com.demo.MiniHotel.modules.phuthu.service.IPhuThuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/phu-thu")
@RequiredArgsConstructor
public class PhuThuController {
    private final IPhuThuService PhuThuService;
    private final IChiTietPhuThuService chiTietPhuThuService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> addNewPhuThu(@RequestBody PhuThuRequest request) throws Exception {
        PhuThuResponse response = PhuThuService.addNewPhuThu(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PhuThuResponse>> getAllPhuThu(){
        List<PhuThuResponse> responses = PhuThuService.getAllPhuThuResponse();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPhuThuById(@PathVariable("id") Integer id) throws Exception {
        PhuThuResponse response = PhuThuService.getPhuThuResponseById(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePhuThu(@PathVariable("id") Integer id,
                                                     @RequestBody PhuThuRequest request) throws Exception {
        PhuThuResponse response = PhuThuService.updatePhuThu(request, id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhuThu(@PathVariable("id") Integer id) throws Exception {
        PhuThuService.deletePhuThu(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }
    @GetMapping("/thong-tin")
    public ResponseEntity<ApiResponse> getAllThongTinPhuThu(){
        List<ThongTinPhuThu> responses = PhuThuService.getAllThongTinPhuThu();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/chi-tiet-thay-doi-gia")
    public ResponseEntity<ApiResponse> getAllChiTietThayDoiGiaPhuThu(){
        List<ChiTietGiaPhuThuResponse> responses = PhuThuService.getChiTietThayDoiGiaPhuThu();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/thay-doi-gia-theo-id")
    public ResponseEntity<ApiResponse> getChiTietThayDoiGiaPhuThuById(
            @RequestParam int idPhuThu,
            @RequestParam int idNhanVien,
            @RequestParam LocalDate ngayCapNhat
    ){
        ChiTietGiaPhuThuResponse response = PhuThuService.getChiTietThayDoiGiaPhuThuById(idPhuThu, idNhanVien, ngayCapNhat);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/thay-doi-gia")
    public ResponseEntity<ApiResponse> themChiTietThayDoiGiaPhuThu(@RequestBody ChiTietGiaPhuThuRequest request) throws Exception {
        ChiTietThayDoiGiaPhuThu response = PhuThuService.themChiTietThayDoiGiaPhuThu(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/thay-doi-gia")
    public ResponseEntity<ApiResponse> capNhatChiTietThayDoiGiaPhuThu(
            @RequestParam int idPhuThu,
            @RequestParam int idNhanVien,
            @RequestParam LocalDate ngayCapNhat,
            @RequestBody ChiTietGiaPhuThuRequest request) throws Exception {
        ChiTietThayDoiGiaPhuThu response = PhuThuService.capNhatChiTietThayDoiGiaPhuThu(idPhuThu, idNhanVien, ngayCapNhat, request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.CREATED);
    }

    @DeleteMapping("/thay-doi-gia")
    public ResponseEntity<ApiResponse> xoaChiTietThayDoiGiaPhuThu(
            @RequestParam int idPhuThu,
            @RequestParam int idNhanVien,
            @RequestParam LocalDate ngayCapNhat) throws Exception {
        PhuThuService.xoaChiTietThayDoiGiaPhuThu(idPhuThu, idNhanVien, ngayCapNhat);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/chi-tiet/phieu-thue")
    public ResponseEntity<ApiResponse> getChiTietPhuThuByIdPhieuThue(@RequestParam int idPhieuThue){
        var response = chiTietPhuThuService.getChiTietPhuThuCuaPhieuThue(idPhieuThue);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }
}
