package com.demo.MiniHotel.modules.hangphong.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.HangPhong;
import com.demo.MiniHotel.model.ThongTinHangPhong;
import com.demo.MiniHotel.modules.hangphong.dto.EditHangPhongResponse;
import com.demo.MiniHotel.modules.hangphong.dto.HangPhongRequest;
import com.demo.MiniHotel.modules.hangphong.dto.HangPhongResponse;
import com.demo.MiniHotel.modules.hangphong.service.IHangPhongService;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongResponse;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/hang-phong")
@RequiredArgsConstructor
@CrossOrigin("*")
public class HangPhongController {
    private final IHangPhongService hangPhongService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> addNewHangPhong(
            @RequestParam("idLoaiPhong") Integer idLoaiPhong,
            @RequestParam("idKieuPhong") Integer idKieuPhong,
            @NotNull @NotBlank @RequestParam("tenHangPhong") String tenHangPhong,
            @RequestParam("moTa") String moTa,
            @RequestParam("giaHangPhong") long giaHangPhong,
            @RequestParam("hinhAnh") MultipartFile hinhAnh
    ) throws Exception {
        HangPhong hangPhong = hangPhongService.addNewHangPhong(
                idLoaiPhong, idKieuPhong, tenHangPhong,moTa, giaHangPhong, hinhAnh
        );
        HangPhongResponse response = convertHangPhongResponse(hangPhong);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HangPhongResponse>> getAllHangPhong() throws Exception {
        List<HangPhong> hangPhongs = hangPhongService.getAllHangPhong();
        List<HangPhongResponse> responses = new ArrayList<>();
        for (HangPhong  hangPhong: hangPhongs) {
            responses.add(convertHangPhongResponse(hangPhong));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditHangPhongResponse> getHangPhongById(@PathVariable("id") Integer id) throws Exception {
        HangPhong hangPhong = hangPhongService.getHangPhongById(id);
        EditHangPhongResponse response = convertEditHangPhongResponse(hangPhong);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateHangPhong(@PathVariable("id") Integer id,
                                                     @RequestParam("idLoaiPhong") Integer idLoaiPhong,
                                                     @RequestParam("idKieuPhong") Integer idKieuPhong,
                                                     @RequestParam("tenHangPhong") String tenHangPhong,
                                                     @RequestParam("moTa") String moTa,
                                                     @RequestParam(value = "hinhAnh", required=false) MultipartFile hinhAnh) throws Exception {
        HangPhong hangPhong = hangPhongService.updateHangPhong(
                idLoaiPhong, idKieuPhong, tenHangPhong,moTa, hinhAnh,id);
        EditHangPhongResponse response = convertEditHangPhongResponse(hangPhong);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteHangPhong(@PathVariable("id") Integer id) throws Exception {
        hangPhongService.deleteHangPhong(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.OK);
    }

    private HangPhongResponse convertHangPhongResponse(HangPhong hangPhong) throws Exception {
        byte[] photoBytes = null;
        Blob photoBlob = hangPhong.getHinhAnh();
        if(photoBlob!=null){
            try {
                photoBytes = photoBlob.getBytes(1, (int)photoBlob.length());
            }catch (SQLException e){
                throw new Exception("Error retrieving photo");
            }
        }
        String base64Photo = Base64.encodeBase64String(photoBytes);
        return new HangPhongResponse(
                hangPhong.getIdHangPhong(),
                hangPhong.getLoaiPhong(),
                hangPhong.getKieuPhong(),
                hangPhong.getTenHangPhong(),
                hangPhong.getMoTa(),
                base64Photo
        );
    }

    private EditHangPhongResponse convertEditHangPhongResponse(HangPhong hangPhong) throws Exception {
        byte[] photoBytes = null;
        Blob photoBlob = hangPhong.getHinhAnh();
        if(photoBlob!=null){
            try {
                photoBytes = photoBlob.getBytes(1, (int)photoBlob.length());
            }catch (SQLException e){
                throw new Exception("Error retrieving photo");
            }
        }
        String base64Photo = Base64.encodeBase64String(photoBytes);
        return new EditHangPhongResponse(
                hangPhong.getIdHangPhong(),
                hangPhong.getLoaiPhong().getIdLoaiPhong(),
                hangPhong.getKieuPhong().getIdKieuPhong(),
                hangPhong.getTenHangPhong(),
                hangPhong.getMoTa(),
                base64Photo
        );
    }
}
