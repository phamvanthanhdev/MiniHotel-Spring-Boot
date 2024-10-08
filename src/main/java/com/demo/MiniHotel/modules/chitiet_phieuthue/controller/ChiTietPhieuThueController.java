package com.demo.MiniHotel.modules.chitiet_phieuthue.controller;

import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietKhachThueResponse;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueRequest;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueResponse;
import com.demo.MiniHotel.modules.chitiet_phieuthue.service.IChiTietPhieuThueService;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuRequest;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuResponse;
import com.demo.MiniHotel.modules.chitiet_phuthu.service.IChiTietPhuThuService;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuRequest;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuResponse;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.service.IChiTietSuDungDichVuService;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonRequest;
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
    @PutMapping("/ngay-di/{id}")
    public ResponseEntity<ChiTietPhieuThueResponse> capNhatNgayDi(@PathVariable("id") Integer id,
                                                        @RequestParam("ngayDi") LocalDate ngayDi) throws Exception {
        ChiTietPhieuThueResponse response = chiTietPhieuThueService.updateNgayDi(id, ngayDi);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChiTietPhieuThueById(@PathVariable("id") Integer id) throws Exception {
        chiTietPhieuThueService.deleteChiTietPhieuThue(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }

    // Lấy danh sách chi tiết thuê phòng của một phiếu thuê phòng
    @GetMapping("/phieu-thue/{idPhieuThue}")
    public ResponseEntity<List<ChiTietPhieuThueResponse>> getChiTietPhieuThuesByIdPhieuThue(@PathVariable("idPhieuThue") Integer id) throws Exception {
        List<ChiTietPhieuThueResponse> responses = chiTietPhieuThueService.getChiTietPhieuThueResponseByIdPhieuThue(id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    //Thêm khách hàng vào chi tiết phiếu thuê
    @PostMapping("/add-khach-thue")
    public ResponseEntity<ResultResponse> addKhachHangToChiTietPhieuThue(@RequestBody ChiTietKhachThueRequest request){
//        ChiTietPhieuThue chiTietPhieuThue = null;
        try {
            chiTietPhieuThueService.addKhachHangToChiTietPhieuThue(request);
            ResultResponse response = new ResultResponse(200, "Thêm khách lưu trú thành công!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ResultResponse response = new ResultResponse(400, "Thêm khách lưu trú không thành công! Vui lòng thử lại");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //Xóa khách hàng khỏi chi tiết phiếu thuê
    @DeleteMapping("/del-khach-thue")
    public ResponseEntity<ResultResponse> removeKhachHangToChiTietPhieuThue(@RequestParam("idChiTietPhieuThue") int idChiTietPhieuThue,
                                                                            @RequestParam("idKhachThue") int idKhachThue) throws Exception {
        DelChiTietKhachThueRequest delChiTietKhachThueRequest = new DelChiTietKhachThueRequest();
        delChiTietKhachThueRequest.setIdChiTietPhieuThue(idChiTietPhieuThue);
        delChiTietKhachThueRequest.setIdKhachThue(idKhachThue);
        try {
            chiTietPhieuThueService.removeKhachHangInChiTietPhieuThue(delChiTietKhachThueRequest);
            ResultResponse response = new ResultResponse(200, "Xóa khách lưu trú thành công!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ResultResponse response = new ResultResponse(400, "Xóa khách lưu trú không thành công! Vui lòng thử lại");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<HoaDonResponse> traPhongKhachSanKhachDoan(@RequestParam("idNhanVien") Integer idNhanVien,
                                                           @RequestParam("tongTien") Long tongTien,
                                                           @RequestParam("ngayTao") LocalDate ngayTao,
                                                           @RequestParam("idChiTietPhieuThues") List<Integer> idChiTietPhieuThues) throws Exception {
        HoaDonResponse hoaDonResponse = chiTietPhieuThueService.traPhongKhachSanKhachDoan(idNhanVien, tongTien, ngayTao, idChiTietPhieuThues);
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

    //
    /*@PutMapping("/dich-vu/{idChiTietSuDungDichVu}")
    public ResponseEntity<ChiTietSuDungDichVu> updateSoLuongChiTietSuDungDichVu(@PathVariable("idChiTietSuDungDichVu") Integer idChiTietSuDungDichVu,
                                                                                @RequestBody ChiTietPhuThuRequest chiTietSuDungDichVuRequest) throws Exception {
        ChiTietSuDungDichVu chiTietSuDungDichVu = chiTietSuDungDichVuService.updateChiTietDichVu(chiTietSuDungDichVuRequest, idChiTietSuDungDichVu);
        return new ResponseEntity<>(chiTietSuDungDichVu, HttpStatus.OK);
    }

    @DeleteMapping("/dich-vu/{idChiTietSuDungDichVu}")
    public ResponseEntity<String> deleteChiTietSuDungDichVu(@PathVariable("idChiTietSuDungDichVu") Integer idChiTietSuDungDichVu) throws Exception {
        chiTietSuDungDichVuService.deleteChiTietSuDungDichVu(idChiTietSuDungDichVu);
        return new ResponseEntity<>("Deleted No." + idChiTietSuDungDichVu + " successfully.", HttpStatus.OK);
    }

    //Lấy hóa đơn của một chi tiết phiếu thuê
    @GetMapping("/hoa-don/{id}")
    public ResponseEntity<HoaDon> getHoaDonByIdChiTietPhieuThue(@PathVariable("id") Integer id) throws Exception {
        HoaDon hoaDon = chiTietPhieuThueService.getHoaDonByChiTietPhieuThue(id);
        return new ResponseEntity<>(hoaDon, HttpStatus.OK);
    }

    @PutMapping("/hoa-don/{id}")
    public ResponseEntity<ChiTietPhieuThue> addHoaDonToChiTietPhieuThue(@PathVariable("id") Integer id, @RequestBody ChiTietPhieuThueRequest request) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = chiTietPhieuThueService.addHoaDonToChiTietPhieuThue(id, request);
        return new ResponseEntity<>(chiTietPhieuThue, HttpStatus.OK);
    }

    //Lấy hóa đơn của một chi tiết sử dụng dịch vụ
    @GetMapping("/dich-vu/hoa-don/{idChiTietSuDungDichVu}")
    public ResponseEntity<HoaDon> getHoaDonByIdChiTietSuDungDichVu(@PathVariable("idChiTietSuDungDichVu") Integer id) throws Exception {
        HoaDon hoaDon = chiTietSuDungDichVuService.getHoaDonInChiTietSuDungDichVu(id);
        return new ResponseEntity<>(hoaDon, HttpStatus.OK);
    }

    @PutMapping("/dich-vu/hoa-don/{idChiTietSuDungDichVu}")
    public ResponseEntity<ChiTietSuDungDichVu> addHoaDonToChiTietSuDung(@PathVariable("idChiTietSuDungDichVu") Integer id, @RequestParam("soHoaDon") String soHoaDon) throws Exception {
        ChiTietSuDungDichVu chiTietSuDungDichVu = chiTietSuDungDichVuService.addHoaDonToChiTietSuDungDichVu(id, soHoaDon);
        return new ResponseEntity<>(chiTietSuDungDichVu, HttpStatus.OK);
    }*/
}
