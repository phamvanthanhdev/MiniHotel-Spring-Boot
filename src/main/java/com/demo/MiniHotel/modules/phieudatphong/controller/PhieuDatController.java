package com.demo.MiniHotel.modules.phieudatphong.controller;

import com.demo.MiniHotel.model.ChiTietPhieuDat;
import com.demo.MiniHotel.model.HoaDon;
import com.demo.MiniHotel.model.PhieuDatPhong;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse2;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatRequest;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatThoiGianResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.phieudatphong.exception.SoLuongPhongTrongException;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/phieu-dat")
@RequiredArgsConstructor
public class PhieuDatController {
    private final IPhieuDatService PhieuDatPhongService;
    @PostMapping("/")
    public ResponseEntity<ResultResponse> datPhongKhachSan(@RequestBody PhieuDatRequest request){
        ResultResponse response;
        try {
            PhieuDatPhongService.addNewPhieuDatPhong(request);
        }catch (SoLuongPhongTrongException ex){
            response = new ResultResponse(400, ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        response = new ResultResponse(200, "Đặt phòng thành công");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PhieuDatPhong>> getAllPhieuDatPhong(){
        List<PhieuDatPhong> PhieuDatPhongs = PhieuDatPhongService.getAllPhieuDatPhong();
        return new ResponseEntity<>(PhieuDatPhongs, HttpStatus.OK);
    }

    @GetMapping("/ngay")
    public ResponseEntity<List<PhieuDatThoiGianResponse>> getPhieuDatTheoNgay(@RequestParam("ngay")LocalDate ngay){
        List<PhieuDatThoiGianResponse> responses = PhieuDatPhongService.getPhieuDatPhongTheoNgay(ngay);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/khach-hang/{idKhachHang}")
    public ResponseEntity<List<PhieuDatResponse>> getPhieuDatByIdKhachHang(
                                                        @PathVariable("idKhachHang") Integer idKhachHang) throws Exception {
        List<PhieuDatResponse> phieuDatResponses = PhieuDatPhongService.getPhieuDatPhongByIdKhachHang(idKhachHang);
        return new ResponseEntity<>(phieuDatResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuDatResponse> getPhieuDatPhongById(@PathVariable("id") Integer id) throws Exception {
        PhieuDatResponse response = PhieuDatPhongService.getPhieuDatResponseById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/chi-tiet/{id}")
    public ResponseEntity<List<ChiTietPhieuDatResponse>> getChiTietPhieuDatsByIdPhieuDat(@PathVariable("id") Integer id) throws Exception {
        List<ChiTietPhieuDatResponse> responses = PhieuDatPhongService.getChiTietPhieuDatsByIdPhieuDat(id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/chi-tiet-2/{id}")
    public ResponseEntity<List<ChiTietPhieuDatResponse2>> getChiTietPhieuDatsByIdPhieuDat2(@PathVariable("id") Integer id) throws Exception {
        List<ChiTietPhieuDatResponse2> response2s = PhieuDatPhongService.getChiTietPhieuDatsByIdPhieuDat2(id);
        return new ResponseEntity<>(response2s, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhieuDatPhong> updatePhieuDatPhong(@PathVariable("id") Integer id,
                                                   @RequestBody PhieuDatRequest request) throws Exception {
        PhieuDatPhong PhieuDatPhong = PhieuDatPhongService.updatePhieuDatPhong(request,id);
        return new ResponseEntity<>(PhieuDatPhong, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhieuDatPhong(@PathVariable("id") Integer id) throws Exception {
        PhieuDatPhongService.deletePhieuDatPhong(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }



    //Nhan vien xac nhan don dat phong
}
