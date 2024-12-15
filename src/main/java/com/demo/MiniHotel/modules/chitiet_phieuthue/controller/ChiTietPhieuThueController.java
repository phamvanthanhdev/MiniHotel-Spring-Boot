package com.demo.MiniHotel.modules.chitiet_phieuthue.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.ChiTietPhieuThue;
import com.demo.MiniHotel.model.ChiTietPhuThu;
import com.demo.MiniHotel.model.ChiTietSuDungDichVu;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.*;
import com.demo.MiniHotel.modules.chitiet_phieuthue.service.IChiTietPhieuThueService;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.CapNhatChiTietPhuThuRequest;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuRequest;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuResponse;
import com.demo.MiniHotel.modules.chitiet_phuthu.service.IChiTietPhuThuService;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.CapNhatChiTietSuDungDichVuRequest;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuRequest;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuResponse;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.service.IChiTietSuDungDichVuService;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.phieuthuephong.dto.ChiTietKhachThueRequest;
import com.demo.MiniHotel.modules.phieuthuephong.dto.DelChiTietKhachThueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/chi-tiet")
@RequiredArgsConstructor
public class ChiTietPhieuThueController {
    private final IChiTietPhieuThueService chiTietPhieuThueService;
    private final IChiTietSuDungDichVuService chiTietSuDungDichVuService;
    private final IChiTietPhuThuService chiTietPhuThuService;

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietPhieuThueResponse> getChiTietPhieuThueResponseById(@PathVariable("id") Integer id) throws Exception {
        ChiTietPhieuThueResponse response = chiTietPhieuThueService.getChiTietPhieuThueResponseById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Khi muốn thuê thêm vài ngày
//    @PutMapping("/ngay-di/{id}")
//    public ResponseEntity<ChiTietPhieuThueResponse> capNhatNgayDi(@PathVariable("id") Integer id,
//                                                        @RequestParam("ngayDi") LocalDate ngayDi) throws Exception {
//        ChiTietPhieuThueResponse response = chiTietPhieuThueService.updateNgayDi(id, ngayDi);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteChiTietPhieuThueById(@PathVariable("id") Integer id) throws Exception {
        chiTietPhieuThueService.deleteChiTietPhieuThue(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .message("Xóa chi tiết phiếu thuê thành công")
                .build(), HttpStatus.OK);
    }

    // Lấy danh sách chi tiết thuê phòng của một phiếu thuê phòng
    @GetMapping("/phieu-thue/{idPhieuThue}")
    public ResponseEntity<List<ChiTietPhieuThueResponse>> getChiTietPhieuThuesByIdPhieuThue(@PathVariable("idPhieuThue") Integer id) throws Exception {
        List<ChiTietPhieuThueResponse> responses = chiTietPhieuThueService.getChiTietPhieuThueResponseByIdPhieuThue(id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    //Thêm khách hàng vào chi tiết phiếu thuê
    @PostMapping("/add-khach-thue")
    public ResponseEntity<ApiResponse> addKhachHangToChiTietPhieuThue(@RequestBody ChiTietKhachThueRequest request) throws Exception {
//        try {
//            chiTietPhieuThueService.addKhachHangToChiTietPhieuThue(request);
//            ResultResponse response = new ResultResponse(200, "Thêm khách lưu trú thành công!");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            ResultResponse response = new ResultResponse(400, "Thêm khách lưu trú không thành công! Vui lòng thử lại");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }

        ChiTietPhieuThue chiTietPhieuThue = chiTietPhieuThueService.addKhachHangToChiTietPhieuThue(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(chiTietPhieuThue)
                .build(), HttpStatus.OK);
    }

    //Xóa khách hàng khỏi chi tiết phiếu thuê
    @DeleteMapping("/del-khach-thue")
    public ResponseEntity<ApiResponse> removeKhachHangCuaChiTietPhieuThue(@RequestParam("idChiTietPhieuThue") int idChiTietPhieuThue,
                                                                            @RequestParam("idKhachThue") int idKhachThue) throws Exception {
        DelChiTietKhachThueRequest delChiTietKhachThueRequest = new DelChiTietKhachThueRequest();
        delChiTietKhachThueRequest.setIdChiTietPhieuThue(idChiTietPhieuThue);
        delChiTietKhachThueRequest.setIdKhachThue(idKhachThue);

        ChiTietPhieuThue chiTietPhieuThue = chiTietPhieuThueService.removeKhachHangInChiTietPhieuThue(delChiTietKhachThueRequest);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(chiTietPhieuThue)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/khach-thue/hien-tai")
    public ResponseEntity<List<ChiTietKhachThueResponse>> getKhachThueHienTai() throws Exception {
        List<ChiTietKhachThueResponse> responses = chiTietPhieuThueService.getKhachThueHienTai();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    //Thêm một chi tiết sử dụng dịch vụ cho một chi tiết phiếu thue
    @PostMapping("/dich-vu")
    public ResponseEntity<List<ChiTietSuDungDichVuResponse>> addChiTietSuDungDichVu(@RequestBody ChiTietSuDungDichVuRequest request) throws Exception {
        List<ChiTietSuDungDichVuResponse> responses = chiTietSuDungDichVuService.themChiTietSuDungDichVu(request);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    //Lấy danh sách chi tiết sử dụng dịch vụ của một chi tiết phiếu thuê
    @GetMapping("/dich-vu/{idChiTietPhieuThue}")
    public ResponseEntity<List<ChiTietSuDungDichVuResponse>> getChiTietSuDungDichVuByIdChiTietPhieuThue(@PathVariable("idChiTietPhieuThue") Integer idChiTietPhieuThue) throws Exception {
        List<ChiTietSuDungDichVuResponse> chiTietSuDungDichVus = chiTietSuDungDichVuService.getChiTietSuDungDichVuByIdChiTietPhieuThue(idChiTietPhieuThue);
        return new ResponseEntity<>(chiTietSuDungDichVus, HttpStatus.OK);
    }

    //Thêm một chi tiết phụ thu cho một chi tiết phiếu thue
    @PostMapping("/phu-thu")
    public ResponseEntity<List<ChiTietPhuThuResponse>> addChiTietPhuThu(@RequestBody ChiTietPhuThuRequest request) throws Exception {
        List<ChiTietPhuThuResponse> responses = chiTietPhuThuService.themChiTietPhuThu(request);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    //Lấy danh sách chi tiết phụ thu của một chi tiết phiếu thuê
    @GetMapping("/phu-thu/{idChiTietPhieuThue}")
    public ResponseEntity<List<ChiTietPhuThuResponse>> getChiTietPhuThuByIdChiTietPhieuThue(@PathVariable("idChiTietPhieuThue") Integer idChiTietPhieuThue) throws Exception {
        List<ChiTietPhuThuResponse> responses = chiTietPhuThuService.getChiTietPhuThuByIdChiTietPhieuThue(idChiTietPhieuThue);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    //Hoàn thành trả phòng
    @PostMapping("/tra-phong")
    public ResponseEntity<HoaDonResponse> traPhongKhachSanKhachLe(@RequestParam("idNhanVien") Integer idNhanVien,
                                                   @RequestParam("tongTien") Long tongTien,
                                                   @RequestParam("ngayTao") LocalDate ngayTao,
                                                   @RequestParam("idChiTietPhieuThue") Integer idChiTietPhieuThue) throws Exception {
        HoaDonResponse hoaDonResponse = chiTietPhieuThueService.traPhongKhachSan(idNhanVien, tongTien, ngayTao, idChiTietPhieuThue);
        return new ResponseEntity<>(hoaDonResponse, HttpStatus.OK);
    }

    //Hoàn thành trả phòng khach doan
    @PostMapping("/tra-phong/khach-doan")
    public ResponseEntity<HoaDonResponse> traPhongKhachSanKhachDoan(@RequestBody TraPhongRequest request) throws Exception {
        HoaDonResponse hoaDonResponse = chiTietPhieuThueService.traPhongKhachSanKhachDoan(request);
        return new ResponseEntity<>(hoaDonResponse, HttpStatus.OK);
    }

    //Đổi phòng khách sạn
    @PutMapping("/doi-phong")
    public ResponseEntity<ResultResponse> doiPhongKhachSan(@RequestParam("idChiTietPhieuThue") Integer idChiTietPhieuThue,
                                                           @RequestParam("maPhong") String maPhong) {
        boolean result = false;
        try {
            result = chiTietPhieuThueService.doiPhong(idChiTietPhieuThue, maPhong);
        } catch (Exception e) {
            ResultResponse response = new ResultResponse(400,
                    e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if(result){
            ResultResponse response = new ResultResponse(200,
                    "Đổi phòng thành công!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            ResultResponse response = new ResultResponse(400,
                    "Hạng phòng này không còn trống!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    // Thêm chi tiết phiếu thuê vào phiếu thuê
    @PostMapping("/them")
    public ResponseEntity<ApiResponse> themChiTietPhieuThue(@RequestBody ChiTietPhieuThueRequest request) throws Exception {
        ChiTietPhieuThue response = chiTietPhieuThueService.themChiTietPhieuThue(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    // Thay đổi thời gian trả phòng
    @PutMapping("/thay-doi-ngay-tra/{id}")
    public ResponseEntity<ApiResponse> thayDoiNgayTraPhong(@PathVariable("id") Integer id,
                                                                        @RequestParam("ngayTraPhong") LocalDate ngayTraPhong)
            throws Exception {
        ChiTietPhieuThueResponse response = chiTietPhieuThueService.thayDoiNgayTraPhong(id, ngayTraPhong);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    // Cập nhật số tiền giảm giá
    @PutMapping("/thay-doi-tien-giam/{id}")
    public ResponseEntity<ApiResponse> thayDoiTienGiamGia(@PathVariable("id") Integer id,
                                                           @RequestParam("tienGiamGia") Long tienGiamGia)
            throws Exception {
        ChiTietPhieuThueResponse response = chiTietPhieuThueService.capNhatTienGiamGia(id, tienGiamGia);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    // Thống kê tần suất thuê của các phòng trong khoảng thời gian
    @GetMapping("/thong-ke-tan-suat")
    public ResponseEntity<ApiResponse> thongKeTanSuatThuePhong(@RequestParam LocalDate ngayBatDau,
                                                               @RequestParam LocalDate ngayKetThuc) throws Exception {
        List<ThongKeTanSuatResponse> responses = chiTietPhieuThueService.thongKeTanSuat(ngayBatDau, ngayKetThuc);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponse> capNhatChiTietPhieuThue(@RequestBody CapNhatChiTietPhieuThueRequest request) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = chiTietPhieuThueService.capNhatChiTietPhieuThue(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(chiTietPhieuThue)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/dich-vu")
    public ResponseEntity<ApiResponse> capNhatChiTietSuDungDichVu(@RequestBody CapNhatChiTietSuDungDichVuRequest request) throws Exception {
        ChiTietSuDungDichVu response = chiTietSuDungDichVuService.capNhatChiTietSuDungDichVu(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/phu-thu")
    public ResponseEntity<ApiResponse> capNhatChiTietPhuThu(@RequestBody CapNhatChiTietPhuThuRequest request) throws Exception {
        ChiTietPhuThu response = chiTietPhuThuService.capNhatChiTietPhuThu(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/dich-vu")
    public ResponseEntity<ApiResponse> xoaChiTietSuDungDichVu(@RequestParam int idChiTietSuDungDichVu) throws Exception {
        chiTietSuDungDichVuService.xoaChiTietSuDungDichVu(idChiTietSuDungDichVu);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/phu-thu")
    public ResponseEntity<ApiResponse> xoaChiTietPhuThu(@RequestParam int idChiTietPhuThu) throws Exception {
        chiTietPhuThuService.xoaChiTietPhuThu(idChiTietPhuThu);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.OK);
    }
}
