package com.demo.MiniHotel.modules.kieuphong.controller;

import com.demo.MiniHotel.model.KieuPhong;
import com.demo.MiniHotel.modules.kieuphong.dto.KieuPhongRequest;
import com.demo.MiniHotel.modules.kieuphong.dto.KieuPhongResponse;
import com.demo.MiniHotel.modules.kieuphong.service.IKieuPhongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kieu-phong")
@RequiredArgsConstructor
@CrossOrigin("*")
public class KieuPhongController {
    private final IKieuPhongService KieuPhongService;
    @PostMapping("/")
    public ResponseEntity<KieuPhong> addNewKieuPhong(@RequestBody KieuPhongRequest request) throws Exception {
        KieuPhong KieuPhong = KieuPhongService.addNewKieuPhong(request);
        return new ResponseEntity<>(KieuPhong, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<KieuPhong>> getAllKieuPhong(){
        List<KieuPhong> KieuPhongs = KieuPhongService.getAllKieuPhong();
        return new ResponseEntity<>(KieuPhongs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KieuPhong> getKieuPhongById(@PathVariable("id") Integer id) throws Exception {
        KieuPhong KieuPhong = KieuPhongService.getKieuPhongById(id);
        return new ResponseEntity<>(KieuPhong, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KieuPhong> updateKieuPhong(@PathVariable("id") Integer id,
                                                     @RequestBody KieuPhongRequest request) throws Exception {
        KieuPhong KieuPhong = KieuPhongService.updateKieuPhong(request,id);
        return new ResponseEntity<>(KieuPhong, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKieuPhong(@PathVariable("id") Integer id) throws Exception {
        KieuPhongService.deleteKieuPhong(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }

    @GetMapping("/so-luong")
    public ResponseEntity<List<KieuPhongResponse>> getKieuPhongUserResponse(){
        List<KieuPhongResponse> responses = KieuPhongService.getKieuPhongResponses();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
