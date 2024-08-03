package com.demo.MiniHotel.modules.trangthai.implement;

import com.demo.MiniHotel.model.TrangThai;
import com.demo.MiniHotel.modules.trangthai.dto.TrangThaiRequest;
import com.demo.MiniHotel.modules.trangthai.service.ITrangThaiService;
import com.demo.MiniHotel.repository.TrangThaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrangThaiImplement implements ITrangThaiService {
    private final TrangThaiRepository repository;
    @Override
    public TrangThai addNewTrangThai(TrangThaiRequest request) {
        TrangThai TrangThai = new TrangThai();
        TrangThai.setTenTrangThai(request.getTenTrangThai());

        return repository.save(TrangThai);
    }

    @Override
    public List<TrangThai> getAllTrangThai() {
        return repository.findAll();
    }

    @Override
    public TrangThai getTrangThaiById(Integer id) throws Exception {
        Optional<TrangThai> TrangThaiOptional = repository.findById(id);
        if(TrangThaiOptional.isEmpty()){
            throw new Exception("TrangThai not found");
        }
        return TrangThaiOptional.get();
    }

    @Override
    public TrangThai updateTrangThai(TrangThaiRequest request, Integer id) throws Exception {
        TrangThai TrangThai = getTrangThaiById(id);
        if(request.getTenTrangThai() != null)
            TrangThai.setTenTrangThai(request.getTenTrangThai());
        return repository.save(TrangThai);
    }

    @Override
    public void deleteTrangThai(Integer id) throws Exception {
        Optional<TrangThai> TrangThaiOptional = repository.findById(id);
        if(TrangThaiOptional.isEmpty()){
            throw new Exception("TrangThai not found");
        }
        repository.deleteById(id);
    }
}
