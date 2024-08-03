package com.demo.MiniHotel.modules.thongtin_hangphong.controller;

import com.demo.MiniHotel.model.ThongTinHangPhong;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongResponse;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongUserResponse;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/thong-tin-hang-phong")
@RequiredArgsConstructor
public class ThongTinHangPhongController {
    private final IThongTinHangPhongService thongTinHangPhongService;

    @GetMapping("/{id}")
    public ResponseEntity<ThongTinHangPhongResponse> getThongTinHangPhongById(@PathVariable("id") Integer id) throws Exception {
        ThongTinHangPhong thongTinHangPhong = thongTinHangPhongService.getThongTinHangPhongById(id);
        ThongTinHangPhongResponse response = thongTinHangPhongService.convertThongTinHangPhongResponse(thongTinHangPhong);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/so-luong/{id}")
    public ResponseEntity<ThongTinHangPhongUserResponse> getThongTinHangPhongSoLuongById(@PathVariable("id") Integer id,
                                                                                         @RequestParam("ngayDenDat") LocalDate ngayDenDat,
                                                                                         @RequestParam("ngayDiDat") LocalDate ngayDiDat) throws Exception {
        ThongTinHangPhongUserResponse response = thongTinHangPhongService.getThongTinHangPhongSoLuongById(id, ngayDenDat, ngayDiDat);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ThongTinHangPhongResponse>> getAllHangPhong() throws Exception {
        List<ThongTinHangPhong> thongTinHangPhongs = thongTinHangPhongService.getAllThongTinHangPhong();
        List<ThongTinHangPhongResponse> responses = new ArrayList<>();
        for (ThongTinHangPhong thongTinHangPhong: thongTinHangPhongs) {
            responses.add(thongTinHangPhongService.convertThongTinHangPhongResponse(thongTinHangPhong));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/kiem-tra")
    public ResponseEntity<String> kiemTraHangPhongTrong(@RequestParam("idHangPhong") Integer idHangPhong,
                                                        @RequestParam("ngayDenDat") LocalDate ngayDenDat,
                                                        @RequestParam("ngayDiDat") LocalDate ngayDiDat,
                                                        @RequestParam("soLuongDat") Integer soLuongDat) {
        Boolean ketQua = thongTinHangPhongService.kiemTraPhongHangPhongTrong(idHangPhong, ngayDenDat, ngayDiDat, soLuongDat);
        String message;
        if(ketQua)
            message = "Số lượng HangPhong còn lại đủ để đặt";
        else
            message = "Số lượng HangPhong còn lại không đủ để đặt";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/kiem-tra/thue-phong")
    public ResponseEntity<ResultResponse> kiemTraHangPhongTrongDeThue(@RequestParam("idHangPhong") Integer idHangPhong,
                                                                        @RequestParam("ngayBatDauThueThem") LocalDate ngayBatDauThueThem,
                                                                        @RequestParam("ngayKetThucThueThem") LocalDate ngayKetThucThueThem) {
        Boolean ketQua = thongTinHangPhongService.kiemTraPhongHangPhongTrong(idHangPhong, ngayBatDauThueThem, ngayKetThucThueThem, 1);
        ResultResponse response = new ResultResponse();
        if(ketQua) {
            response.setCode(200);
            response.setMessage("Số lượng phòng còn lại đủ để đặt");
        } else {
            response.setCode(400);
            response.setMessage("Số lượng phòng còn lại không đủ để đặt");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/so-luong")
    public ResponseEntity<Integer> soLuongHangPhongTrong(@RequestParam("ngayDenDat") LocalDate ngayDenDat,
                                                                                 @RequestParam("ngayDiDat") LocalDate ngayDiDat,
                                                                                 @RequestParam("idHangPhong") Integer idHangPhong) throws Exception {
        Integer soLuongHangPhongTrong = thongTinHangPhongService.laySoLuongHangPhongTrong(ngayDenDat, ngayDiDat, idHangPhong);

        return new ResponseEntity<>(soLuongHangPhongTrong, HttpStatus.OK);
    }

    @GetMapping("/thoi-gian")
    public ResponseEntity<List<ThongTinHangPhongUserResponse>> getHangPhongTheoThoiGian(@RequestParam("ngayDenDat") LocalDate ngayDenDat,
                                                                                        @RequestParam("ngayDiDat") LocalDate ngayDiDat) throws Exception {
        List<ThongTinHangPhongUserResponse> response2s = thongTinHangPhongService.getThongTinHangPhongTheoThoiGian(ngayDenDat, ngayDiDat);
        return new ResponseEntity<>(response2s, HttpStatus.OK);
    }

    @GetMapping("/tim-kiem")
    public ResponseEntity<List<ThongTinHangPhongUserResponse>> timKiemHangPhong(@RequestParam("ngayDenDat") LocalDate ngayDenDat,
                                                                                @RequestParam("ngayDiDat") LocalDate ngayDiDat,
                                                                                @RequestParam("soNguoi") Integer soNguoi,
                                                                                @RequestParam("giaMin") Long giaMin,
                                                                                @RequestParam("giaMax") Long giaMax) throws Exception {
        List<ThongTinHangPhongUserResponse> response2s = thongTinHangPhongService.timKiemThongTinHangPhong(ngayDenDat, ngayDiDat, soNguoi, giaMin, giaMax);
        return new ResponseEntity<>(response2s, HttpStatus.OK);
    }

    @GetMapping("/sap-xep")
    public ResponseEntity<List<ThongTinHangPhongUserResponse>> sapXepHangPhongTheoSoLuongDatThue(@RequestParam("ngayDenDat") LocalDate ngayDenDat,
                                                                                             @RequestParam("ngayDiDat") LocalDate ngayDiDat) throws Exception {
        List<ThongTinHangPhongUserResponse> responses = thongTinHangPhongService.sapXepHangPhongTheoSoLuongDatThue(ngayDenDat, ngayDiDat);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}