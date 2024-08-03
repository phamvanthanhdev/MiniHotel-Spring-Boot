package com.demo.MiniHotel.modules.bophan.controller;

import com.demo.MiniHotel.modules.bophan.dto.BoPhanRequest;
import com.demo.MiniHotel.model.BoPhan;
import com.demo.MiniHotel.modules.bophan.service.IBoPhanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bo-phan")
@RequiredArgsConstructor
public class BoPhanController {
    private final IBoPhanService boPhanService;
    @PostMapping("/")
    public ResponseEntity<BoPhan> addNewBoPhan(@RequestBody BoPhanRequest request){
        BoPhan boPhan = boPhanService.addNewBoPhan(request);
        return new ResponseEntity<>(boPhan, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<BoPhan>> getAllBoPhan(){
        List<BoPhan> boPhans = boPhanService.getAllBoPhan();
        return new ResponseEntity<>(boPhans, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoPhan> getBoPhanById(@PathVariable("id") Integer id) throws Exception {
        BoPhan boPhan = boPhanService.getBoPhanById(id);
        return new ResponseEntity<>(boPhan, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoPhan> updateBoPhan(@PathVariable("id") Integer id,
                                               @RequestBody BoPhanRequest request) throws Exception {
        BoPhan boPhan = boPhanService.updateBoPhan(request,id);
        return new ResponseEntity<>(boPhan, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoPhan(@PathVariable("id") Integer id) throws Exception {
        boPhanService.deleteBoPhan(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }
}
