package com.demo.MiniHotel.modules.nhomquyen.controller;


import com.demo.MiniHotel.model.NhomQuyen;
import com.demo.MiniHotel.modules.nhomquyen.dto.NhomQuyenRequest;
import com.demo.MiniHotel.modules.nhomquyen.service.INhomQuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhom-quyen")
@RequiredArgsConstructor
public class NhomQuyenController {
    private final INhomQuyenService nhomQuyenService;
    @PostMapping("/")
    public ResponseEntity<NhomQuyen> addNewNhomQuyen(@RequestBody NhomQuyenRequest request){
        NhomQuyen nhomQuyen = nhomQuyenService.addNewNhomQuyen(request);
        return new ResponseEntity<>(nhomQuyen, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<NhomQuyen>> getAllNhomQuyen(){
        List<NhomQuyen> nhomQuyens = nhomQuyenService.getAllNhomQuyen();
        return new ResponseEntity<>(nhomQuyens, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhomQuyen> getNhomQuyenById(@PathVariable("id") Integer id) throws Exception {
        NhomQuyen nhomQuyen = nhomQuyenService.getNhomQuyenById(id);
        return new ResponseEntity<>(nhomQuyen, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NhomQuyen> updateNhomQuyen(@PathVariable("id") Integer id,
                                               @RequestBody NhomQuyenRequest request) throws Exception {
        NhomQuyen nhomQuyen = nhomQuyenService.updateNhomQuyen(request,id);
        return new ResponseEntity<>(nhomQuyen, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNhomQuyen(@PathVariable("id") Integer id) throws Exception {
        nhomQuyenService.deleteNhomQuyen(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }
}
