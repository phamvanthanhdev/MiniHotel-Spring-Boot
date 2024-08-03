package com.demo.MiniHotel.modules.phong.controller;

import com.demo.MiniHotel.model.Phong;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.phong.dto.PhongRequest;
import com.demo.MiniHotel.modules.phong.service.IPhongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/phong")
public class PhongController {
    private final IPhongService PhongService;
    @PostMapping("/")
    public ResponseEntity<Phong> addNewPhong(@RequestBody PhongRequest request) throws Exception {
        Phong Phong = PhongService.addNewPhong(request);
        return new ResponseEntity<>(Phong, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Phong>> getAllPhong(){
        List<Phong> Phongs = PhongService.getAllPhong();
        return new ResponseEntity<>(Phongs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Phong> getPhongById(@PathVariable("id") String id) throws Exception {
        Phong Phong = PhongService.getPhongById(id);
        return new ResponseEntity<>(Phong, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Phong> updatePhong(@PathVariable("id") String id,
                                                   @RequestBody PhongRequest request) throws Exception {
        Phong Phong = PhongService.updatePhong(request,id);
        return new ResponseEntity<>(Phong, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhong(@PathVariable("id") String id) throws Exception {
        PhongService.deletePhong(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }

    @PostMapping("/trang-thai")
    public ResponseEntity<ResultResponse> capNhatTrangThaiPhong(@RequestParam("idTrangThai") int idTrangThai,
                                                                @RequestParam("maPhong") String maPhong) throws Exception {
        PhongService.capNhatTrangThai(maPhong, idTrangThai);
        ResultResponse response = new ResultResponse(
                200, "Cập nhật trạng thái phòng thành công!"
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
