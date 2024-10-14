package com.demo.MiniHotel.modules.thongtin_phong.controller;

import com.demo.MiniHotel.model.Phong;
import com.demo.MiniHotel.modules.thongtin_phong.dto.PhongTrongResponse;
import com.demo.MiniHotel.modules.thongtin_phong.dto.ThongTinPhongHienTaiResponse;
import com.demo.MiniHotel.modules.thongtin_phong.dto.ThongTinPhongResponse;
import com.demo.MiniHotel.modules.thongtin_phong.service.IThongTinPhongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/thong-tin-phong")
@RequiredArgsConstructor
public class ThongTinPhongController {
    private final IThongTinPhongService thongTinPhongService;

    @GetMapping("/thoi-gian")
    public ResponseEntity<List<ThongTinPhongResponse>> getThongTinPhongsTheoThoiGian(@RequestParam("ngayDenThue") LocalDate ngayDenThue,
                                                                                     @RequestParam("ngayDiThue") LocalDate ngayDiThue){
        List<ThongTinPhongResponse> responses = thongTinPhongService.getThongTinPhongTheoThoiGian(ngayDenThue, ngayDiThue);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/hien-tai")
    public ResponseEntity<List<ThongTinPhongHienTaiResponse>> getThongTinPhongsHienTai(){
        List<ThongTinPhongHienTaiResponse> responses = thongTinPhongService.getThongTinPhongHienTai();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/phong-trong")
    public ResponseEntity<List<PhongTrongResponse>> getPhongTrongByIdHangPhong(@RequestParam("idHangPhong") int idHangPhong,
                                                                               @RequestParam("ngayDenThue") LocalDate ngayDenThue,
                                                                               @RequestParam("ngayDiThue") LocalDate ngayDiThue){
        List<PhongTrongResponse> responses = thongTinPhongService.getPhongTrongByIdHangPhong(idHangPhong, ngayDenThue, ngayDiThue);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/hang-phong")
    public ResponseEntity<List<ThongTinPhongResponse>> getThongTinPhongsTheoThoiGian(@RequestParam("ngayDenThue") LocalDate ngayDenThue,
                                                                                     @RequestParam("ngayDiThue") LocalDate ngayDiThue,
                                                                                     @RequestParam("idHangPhong") int idHangPhong){
        List<ThongTinPhongResponse> responses = thongTinPhongService.getThongTinPhongTheoHangPhong(ngayDenThue, ngayDiThue, idHangPhong);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
