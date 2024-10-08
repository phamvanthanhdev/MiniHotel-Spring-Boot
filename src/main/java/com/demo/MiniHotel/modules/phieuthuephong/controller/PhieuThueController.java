package com.demo.MiniHotel.modules.phieuthuephong.controller;

import com.demo.MiniHotel.model.ChiTietPhieuThue;
import com.demo.MiniHotel.model.PhieuThuePhong;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.phieudatphong.exception.SoLuongPhongTrongException;
import com.demo.MiniHotel.modules.phieuthuephong.dto.ChiTietKhachThueRequest;
import com.demo.MiniHotel.modules.phieuthuephong.dto.DelChiTietKhachThueRequest;
import com.demo.MiniHotel.modules.phieuthuephong.dto.PhieuThuePhongRequest;
import com.demo.MiniHotel.modules.phieuthuephong.dto.PhieuThueResponse;
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
    public ResponseEntity<ResultResponse> thuePhongKhachSan(@RequestBody PhieuThuePhongRequest request) throws Exception {
        ResultResponse response;
        try {
            PhieuThuePhongService.addNewPhieuThuePhong(request);
        }catch (SoLuongPhongTrongException ex){
            response = new ResultResponse(400, ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        response = new ResultResponse(200, "Thuê phòng thành công");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
    public ResponseEntity<List<PhieuThueResponse>> getPhieuThuePhongResponseById(@RequestParam("cmnd") String cmnd) throws Exception {
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
    public ResponseEntity<String> deletePhieuThuePhong(@PathVariable("id") Integer id) throws Exception {
        PhieuThuePhongService.deletePhieuThuePhong(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }

    //Kiểm tra số lượng phòng trả được chọn có phải là tất cả phòng trong phiếu thuê
    @GetMapping("/kiem-tra-so-luong")
    public ResponseEntity<Boolean> kiemTraPhieuThueCuoi(@RequestParam("idPhieuThue") Integer idPhieuThue,
                                                        @RequestParam("soLuong") Integer soLuong) throws Exception {
        Boolean result = PhieuThuePhongService.kiemTraChiTietPhieuThueCuoiCung(soLuong, idPhieuThue);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //Thêm khách hàng vào chi tiết phiếu thuê
    /*@PostMapping("/khach-thue")
    public ResponseEntity<ChiTietPhieuThue> addKhachHangToChiTietPhieuThue(@RequestBody ChiTietKhachThueRequest request) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = PhieuThuePhongService.addKhachHangToChiTietPhieuThue(request);
        return new ResponseEntity<>(chiTietPhieuThue, HttpStatus.OK);
    }*/

    //Xóa khách hàng khỏi chi tiết phiếu thuê
    /*@PostMapping("/del-khach-thue")
    public ResponseEntity<ChiTietPhieuThue> removeKhachHangToChiTietPhieuThue(@RequestBody DelChiTietKhachThueRequest request) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = PhieuThuePhongService.removeKhachHangInChiTietPhieuThue(request);
        return new ResponseEntity<>(chiTietPhieuThue, HttpStatus.OK);
    }*/
}
