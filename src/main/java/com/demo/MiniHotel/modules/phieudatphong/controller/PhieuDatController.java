package com.demo.MiniHotel.modules.phieudatphong.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.PhieuDatPhong;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatRequest;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse2;
import com.demo.MiniHotel.modules.chitiet_phieudat.service.IChiTietPhieuDatService;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.*;
import com.demo.MiniHotel.modules.phieudatphong.exception.SoLuongPhongTrongException;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
import com.demo.MiniHotel.vnpay.PhieuDatThanhToanRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/phieu-dat")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PhieuDatController {
    private final IPhieuDatService PhieuDatPhongService;
    private final IChiTietPhieuDatService chiTietPhieuDatService;
    /*@PostMapping("/")
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
    }*/

    @PostMapping("/dat-phong")
    public ResponseEntity<ResultResponse> datPhongKhachSan(@RequestBody PhieuDatThanhToanRequest request){
        ResultResponse response;
        try {
            PhieuDatPhongService.datPhongKhachSan(request);
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

    @PostMapping("/ngay")
    public ResponseEntity<List<PhieuDatThoiGianResponse>> getPhieuDatTheoNgay(@RequestBody PhieuDatTheoNgayRequest request){
        List<PhieuDatThoiGianResponse> responses = PhieuDatPhongService.getPhieuDatPhongTheoNgay(request);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/thoi-gian")
    public ResponseEntity<List<PhieuDatThoiGianResponse>> getPhieuDatTheoThoiGian(@RequestParam("ngayBatDauTim")LocalDate ngayBatDauTim,
                                                                                  @RequestParam("ngayKetThucTim")LocalDate ngayKetThucTim){
        List<PhieuDatThoiGianResponse> responses = PhieuDatPhongService.getPhieuDatPhongTheoGian(ngayBatDauTim, ngayKetThucTim);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

//    @GetMapping("/khach-hang/{idKhachHang}")
//    public ResponseEntity<List<PhieuDatResponse>> getPhieuDatByIdKhachHang(
//                                                        @PathVariable("idKhachHang") Integer idKhachHang) throws Exception {
//        List<PhieuDatResponse> phieuDatResponses = PhieuDatPhongService.getPhieuDatPhongByIdKhachHang(idKhachHang);
//        return new ResponseEntity<>(phieuDatResponses, HttpStatus.OK);
//    }

    @GetMapping("/khach-hang")
    public ResponseEntity<List<PhieuDatUserResponse>> getPhieuDatByIdKhachHang(
            /*@PathVariable("idKhachHang") Integer idKhachHang*/) throws Exception {
        List<PhieuDatUserResponse> phieuDatResponses = PhieuDatPhongService.getPhieuDatUserByIdKhachHang();
        return new ResponseEntity<>(phieuDatResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuDatResponse> getPhieuDatPhongById(@PathVariable("id") Integer id) throws Exception {
        PhieuDatResponse response = PhieuDatPhongService.getPhieuDatResponseById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<PhieuDatDetailsResponse> getPhieuDatDetailsById(@PathVariable("id") Integer id) throws Exception {
        PhieuDatDetailsResponse response = PhieuDatPhongService.getPhieuDatDetailsById(id);
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

//    @GetMapping("/khach-hang")
//    public ResponseEntity<List<PhieuDatResponse>> getPhieuDatPhongTheoCMND(@RequestParam("cmnd") String cmnd) throws Exception {
//        List<PhieuDatResponse> responses = PhieuDatPhongService.getPhieuDatTheoCMND(cmnd);
//        return new ResponseEntity<>(responses, HttpStatus.OK);
//    }

    @PutMapping("/huy-dat")
    public ResponseEntity<ApiResponse> huyDatPhong(@RequestBody HuyDatRequest request) throws Exception {
        PhieuDatPhong phieuDatPhong = PhieuDatPhongService.huyDatPhong(request);

        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(phieuDatPhong)
                .build(), HttpStatus.OK);
    }

    //Cập nhật thông tin ds khách hàng từ file excel
    @PostMapping("/khach-hang/cap-nhat")
    public ResponseEntity<ResultResponse> capNhatThongTinKhachHang(@RequestBody CapNhatKhachHangResquest request){
        try {
            PhieuDatPhongService.capNhatDanhSachKhachHang(request);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResultResponse(400, e.getMessage()),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResultResponse(200, "Successfully"),HttpStatus.OK);
    }

    @GetMapping("/tim-kiem/cccd")
    public ResponseEntity<List<PhieuDatUserResponse>> timKiemPhieuDatTheoCMND(@RequestParam("cccd") String cccd)
            throws Exception {
        List<PhieuDatUserResponse> responses = PhieuDatPhongService.getPhieuDatByCccd(cccd);
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

    // Bo sung danh sách chi tiết phiếu đặt
    @PostMapping("/chi-tiet/bo-sung")
    public ResponseEntity<ApiResponse> boSungChiTietPhieuDat(@RequestBody List<ChiTietPhieuDatRequest> chiTietPhieuDatRequests)
            throws Exception {
        PhieuDatPhongService.boSungChiTietPhieuDat(chiTietPhieuDatRequests);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .message("Thêm chi tiết phiếu đặt thành công")
                .build(), HttpStatus.OK);
    }

    // Xóa chi tiết phiếu đặt
    @PostMapping("/chi-tiet/xoa")
    public ResponseEntity<ApiResponse> xoaChiTietPhieuDat(@RequestBody ChiTietPhieuDatRequest chiTietPhieuDatRequest) {
        chiTietPhieuDatService.xoaChiTietPhieuDat(chiTietPhieuDatRequest);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .message("Thay đổi chi tiết phiếu đặt thành công")
                .build(), HttpStatus.OK);
    }

    //Nhan vien xac nhan don dat phong


}
