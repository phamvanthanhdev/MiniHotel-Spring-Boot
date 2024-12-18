package com.demo.MiniHotel.modules.phieuthuephong.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.ChiTietPhieuThue;
import com.demo.MiniHotel.model.PhieuThuePhong;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.TraPhongRequest;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.TraPhongResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatFilterRequest;
import com.demo.MiniHotel.modules.phieudatphong.dto.QuanLyPhieuDatResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.phieudatphong.exception.SoLuongPhongTrongException;
import com.demo.MiniHotel.modules.phieuthuephong.dto.*;
import com.demo.MiniHotel.modules.phieuthuephong.service.IPhieuThueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/phieu-thue")
@RequiredArgsConstructor
public class PhieuThueController {
    private final IPhieuThueService PhieuThuePhongService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> thuePhongKhachSan(@RequestBody PhieuThuePhongRequest request) throws Exception {


        PhieuThueResponse response = PhieuThuePhongService.addNewPhieuThuePhong(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PhieuThuePhong>> getAllPhieuThuePhong(){
        List<PhieuThuePhong> PhieuThuePhongs = PhieuThuePhongService.getAllPhieuThuePhong();
        return new ResponseEntity<>(PhieuThuePhongs, HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<PhieuThuePhong> getPhieuThuePhongById(@PathVariable("id") Integer id) throws Exception {
//        PhieuThuePhong PhieuThuePhong = PhieuThuePhongService.getPhieuThuePhongById(id);
//        return new ResponseEntity<>(PhieuThuePhong, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuThueResponse> getPhieuThuePhongResponseById(@PathVariable("id") Integer id) throws Exception {
        PhieuThueResponse response = PhieuThuePhongService.getPhieuThuePhongResonseById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/khach-hang")
    public ResponseEntity<List<PhieuThueResponse>> getPhieuThuePhongResponseByCMND(@RequestParam("cmnd") String cmnd) throws Exception {
        List<PhieuThueResponse> responses = PhieuThuePhongService.timKiemPhieuThueTheoCmnd(cmnd);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    //Lấy danh sách chi tiết thuê phòng của một phiếu thuê phòng
    /*@GetMapping("/danh-sach-chi-tiet/{id}")
    public ResponseEntity<List<ChiTietPhieuThue>> getChiTietPhieuThuesByIdPhieuThue(@PathVariable("id") Integer id) throws Exception {
        List<ChiTietPhieuThue> chiTietPhieuThues = PhieuThuePhongService.getChiTietPhieuThuesByIdPhieuThue(id);
        return new ResponseEntity<>(chiTietPhieuThues, HttpStatus.OK);
    }*/

    //Lấy chi tiết đặt phòng theo id
    /*@GetMapping("/chi-tiet/{idChiTiet}")
    public ResponseEntity<ChiTietPhieuThue> getChiTietPhieuThueById(@PathVariable("idChiTiet") Integer idChiTiet) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = PhieuThuePhongService.getChiTietPhieuThueById(idChiTiet);
        return new ResponseEntity<>(chiTietPhieuThue, HttpStatus.OK);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<PhieuThuePhong> updatePhieuThuePhong(@PathVariable("id") Integer id,
                                                   @RequestBody PhieuThuePhongRequest request) throws Exception {
        PhieuThuePhong PhieuThuePhong = PhieuThuePhongService.updatePhieuThuePhong(request,id);
        return new ResponseEntity<>(PhieuThuePhong, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePhieuThuePhong(@PathVariable("id") Integer id) throws Exception {
        PhieuThuePhongService.deletePhieuThuePhong(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.OK);
    }

    //Kiểm tra số lượng phòng trả được chọn có phải là tất cả phòng trong phiếu thuê
    @GetMapping("/kiem-tra-so-luong")
    public ResponseEntity<Boolean> kiemTraPhieuThueCuoi(@RequestParam("idPhieuThue") Integer idPhieuThue,
                                                        @RequestParam("soLuong") Integer soLuong) throws Exception {
        Boolean result = PhieuThuePhongService.kiemTraChiTietPhieuThueCuoiCung(soLuong, idPhieuThue);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //(Khách đoàn) Lấy thông tin phiếu thuê và chi tiết phiếu thuê được chọn để trả phòng
    @PostMapping("/thong-tin-tra-phong")
    public ResponseEntity<ApiResponse> getThongTinTraPhong(@RequestBody ThongTinTraPhongRequest request) throws Exception {
        ThongTinTraPhongResponse response = PhieuThuePhongService.getThongTinTraPhong(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    // (Khách đoàn) Trả phòng
    @PostMapping("/tra-phong")
    public ResponseEntity<ApiResponse> traPhong(@RequestBody TraPhongRequest request) throws Exception {
        TraPhongResponse response = PhieuThuePhongService.traPhong(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/phan-tram-giam")
    public ResponseEntity<ApiResponse> capNhatPhanTramGiam(@RequestParam int idPhieuThue,
                                                           @RequestParam int phanTramGiam) throws Exception {
        PhieuThueResponse response = PhieuThuePhongService.capNhatPhanTramGiam(idPhieuThue, phanTramGiam);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/theo-trang")
    public ResponseEntity<ApiResponse> getPhieuThuePhongTheoTrang(@RequestParam("pageNumber") int pageNumber,
                                                                 @RequestParam("pageSize") int pageSize) throws Exception {
        List<PhieuThueResponse> responses = PhieuThuePhongService.getPhieuThuePhongTheoTrang(pageNumber, pageSize);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/tong-trang")
    public ResponseEntity<ApiResponse> getTongTrangPhieuThuePhong(@RequestParam("pageSize") int pageSize) throws Exception {
        int tongTrang = PhieuThuePhongService.getTongTrangPhieuThuePhong(pageSize);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(tongTrang)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/cap-nhat/{id}") // Xem lại chi tiết phiếu thuê
    public ResponseEntity<PhieuThueResponse> getCapNhatPhieuThuePhongResponseById(@PathVariable("id") Integer id) throws Exception {
        PhieuThueResponse response = PhieuThuePhongService.getCapNhatPhieuThuePhongResonseById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/cap-nhat")
    public ResponseEntity<ApiResponse> capNhatPhieuThuePhong(@RequestBody CapNhatPhieuThueRequest request) throws Exception {
        PhieuThueResponse response = PhieuThuePhongService.capNhatPhieuThuePhong(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<ApiResponse> getPhieuDatPhongFilter(@RequestBody PhieuThueFilterRequest request) throws Exception {
        List<PhieuThueResponse> responses = PhieuThuePhongService.getPhieuThueFilter(request.getLuaChon(),
                request.getNgayBatDauLoc(), request.getNgayKetThucLoc(), request.getTenKhachHang(), request.getNoiDung());
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }
}
