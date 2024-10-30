package com.demo.MiniHotel.modules.loaiphong.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.LoaiPhong;
import com.demo.MiniHotel.modules.loaiphong.dto.LoaiPhongRequest;
import com.demo.MiniHotel.modules.loaiphong.service.ILoaiPhongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loai-phong")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoaiPhongController {
    private final ILoaiPhongService LoaiPhongService;
    @PostMapping("/")
    public ResponseEntity<LoaiPhong> addNewLoaiPhong(@RequestBody LoaiPhongRequest request) throws Exception {
        LoaiPhong LoaiPhong = LoaiPhongService.addNewLoaiPhong(request);
        return new ResponseEntity<>(LoaiPhong, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllLoaiPhong(){
        List<LoaiPhong> LoaiPhongs = LoaiPhongService.getAllLoaiPhong();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(LoaiPhongs)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoaiPhong> getLoaiPhongById(@PathVariable("id") Integer id) throws Exception {
        LoaiPhong LoaiPhong = LoaiPhongService.getLoaiPhongById(id);
        return new ResponseEntity<>(LoaiPhong, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoaiPhong> updateLoaiPhong(@PathVariable("id") Integer id,
                                                     @RequestBody LoaiPhongRequest request) throws Exception {
        LoaiPhong LoaiPhong = LoaiPhongService.updateLoaiPhong(request,id);
        return new ResponseEntity<>(LoaiPhong, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLoaiPhong(@PathVariable("id") Integer id) throws Exception {
        LoaiPhongService.deleteLoaiPhong(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }
}
