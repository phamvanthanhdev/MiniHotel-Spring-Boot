package com.demo.MiniHotel.modules.loaiphong.implement;

import com.demo.MiniHotel.model.LoaiPhong;
import com.demo.MiniHotel.modules.loaiphong.dto.LoaiPhongRequest;
import com.demo.MiniHotel.modules.loaiphong.service.ILoaiPhongService;
import com.demo.MiniHotel.repository.LoaiPhongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoaiPhongImplement implements ILoaiPhongService {
    private final LoaiPhongRepository repository;
    @Override
    public LoaiPhong addNewLoaiPhong(LoaiPhongRequest request) {
        LoaiPhong LoaiPhong = new LoaiPhong();
        LoaiPhong.setTenLoaiPhong(request.getTenLoaiPhong());
        LoaiPhong.setMoTa(request.getMoTa());
        LoaiPhong.setSoNguoiToiDa(request.getSoNguoiToiDa());

        return repository.save(LoaiPhong);
    }

    @Override
    public List<LoaiPhong> getAllLoaiPhong() {
        return repository.findAll();
    }

    @Override
    public LoaiPhong getLoaiPhongById(Integer id) throws Exception {
        Optional<LoaiPhong> LoaiPhongOptional = repository.findById(id);
        if(LoaiPhongOptional.isEmpty()){
            throw new Exception("LoaiPhong not found");
        }
        return LoaiPhongOptional.get();
    }

    @Override
    public LoaiPhong updateLoaiPhong(LoaiPhongRequest request, Integer id) throws Exception {
        LoaiPhong LoaiPhong = getLoaiPhongById(id);
        if(request.getTenLoaiPhong() != null)
            LoaiPhong.setTenLoaiPhong(request.getTenLoaiPhong());
        if(request.getMoTa() != null)
            LoaiPhong.setMoTa(request.getMoTa());
        if(request.getSoNguoiToiDa() != null)
            LoaiPhong.setSoNguoiToiDa(request.getSoNguoiToiDa());
        return repository.save(LoaiPhong);
    }

    @Override
    public void deleteLoaiPhong(Integer id) throws Exception {
        Optional<LoaiPhong> LoaiPhongOptional = repository.findById(id);
        if(LoaiPhongOptional.isEmpty()){
            throw new Exception("LoaiPhong not found");
        }
        repository.deleteById(id);
    }
}
