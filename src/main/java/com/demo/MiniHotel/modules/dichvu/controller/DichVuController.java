package com.demo.MiniHotel.modules.dichvu.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.ChiTietThayDoiGiaDichVu;
import com.demo.MiniHotel.model.DichVu;
import com.demo.MiniHotel.model.ThongTinDichVu;
import com.demo.MiniHotel.model.ThongTinPhuThu;
import com.demo.MiniHotel.modules.dichvu.dto.ChiTietGiaDichVuRequest;
import com.demo.MiniHotel.modules.dichvu.dto.ChiTietGiaDichVuResponse;
import com.demo.MiniHotel.modules.dichvu.dto.DichVuRequest;
import com.demo.MiniHotel.modules.dichvu.dto.DichVuResponse;
import com.demo.MiniHotel.modules.dichvu.service.IDichVuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dich-vu")
@RequiredArgsConstructor
public class DichVuController {
    private final IDichVuService DichVuService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> addNewDichVu(@RequestBody DichVuRequest request) throws Exception {
        DichVuResponse response = DichVuService.addNewDichVu(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DichVuResponse>> getAllDichVu(){
        List<DichVuResponse> responses = DichVuService.getAllDichVuResponse();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDichVuResponseById(@PathVariable("id") Integer id) throws Exception {
        DichVuResponse response = DichVuService.getDichVuResponseById(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateDichVu(@PathVariable("id") Integer id,
                                                     @RequestBody DichVuRequest request) throws Exception {
        DichVuResponse response = DichVuService.updateDichVu(request, id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDichVu(@PathVariable("id") Integer id) throws Exception {
        DichVuService.deleteDichVu(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }

    @GetMapping("/thong-tin")
    public ResponseEntity<ApiResponse> getAllThongTinDichVu(){
        List<ThongTinDichVu> responses = DichVuService.getAllThongTinDichVu();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/chi-tiet-thay-doi-gia")
    public ResponseEntity<ApiResponse> getAllChiTietThayDoiGiaDichVu(){
        List<ChiTietGiaDichVuResponse> responses = DichVuService.getChiTietThayDoiGiaDichVu();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/thay-doi-gia-theo-id")
    public ResponseEntity<ApiResponse> getChiTietThayDoiGiaDichVuById(
            @RequestParam int idDichVu,
            @RequestParam int idNhanVien,
            @RequestParam LocalDate ngayCapNhat
    ){
        ChiTietGiaDichVuResponse response = DichVuService.getChiTietThayDoiGiaDichVuById(idDichVu, idNhanVien, ngayCapNhat);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/thay-doi-gia")
    public ResponseEntity<ApiResponse> themChiTietThayDoiGiaDichVu(@RequestBody ChiTietGiaDichVuRequest request) throws Exception {
        ChiTietThayDoiGiaDichVu response = DichVuService.themChiTietThayDoiGiaDichVu(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/thay-doi-gia")
    public ResponseEntity<ApiResponse> capNhatChiTietThayDoiGiaDichVu(
            @RequestParam int idDichVu,
            @RequestParam int idNhanVien,
            @RequestParam LocalDate ngayCapNhat,
            @RequestBody ChiTietGiaDichVuRequest request) throws Exception {
        ChiTietThayDoiGiaDichVu response = DichVuService.capNhatChiTietThayDoiGiaDichVu(idDichVu, idNhanVien, ngayCapNhat, request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.CREATED);
    }

    @DeleteMapping("/thay-doi-gia")
    public ResponseEntity<ApiResponse> xoaChiTietThayDoiGiaDichVu(
            @RequestParam int idDichVu,
            @RequestParam int idNhanVien,
            @RequestParam LocalDate ngayCapNhat) throws Exception {
        DichVuService.xoaChiTietThayDoiGiaDichVu(idDichVu, idNhanVien, ngayCapNhat);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.CREATED);
    }
}
