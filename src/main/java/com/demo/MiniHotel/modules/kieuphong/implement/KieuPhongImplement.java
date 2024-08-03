package com.demo.MiniHotel.modules.kieuphong.implement;

import com.demo.MiniHotel.model.KieuPhong;
import com.demo.MiniHotel.modules.kieuphong.dto.KieuPhongRequest;
import com.demo.MiniHotel.modules.kieuphong.service.IKieuPhongService;
import com.demo.MiniHotel.repository.KieuPhongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KieuPhongImplement implements IKieuPhongService {
    private final KieuPhongRepository repository;
    @Override
    public KieuPhong addNewKieuPhong(KieuPhongRequest request) {
        KieuPhong KieuPhong = new KieuPhong();
        KieuPhong.setTenKieuPhong(request.getTenKieuPhong());
        KieuPhong.setMoTa(request.getMoTa());

        return repository.save(KieuPhong);
    }

    @Override
    public List<KieuPhong> getAllKieuPhong() {
        return repository.findAll();
    }

    @Override
    public KieuPhong getKieuPhongById(Integer id) throws Exception {
        Optional<KieuPhong> KieuPhongOptional = repository.findById(id);
        if(KieuPhongOptional.isEmpty()){
            throw new Exception("KieuPhong not found");
        }
        return KieuPhongOptional.get();
    }

    @Override
    public KieuPhong updateKieuPhong(KieuPhongRequest request, Integer id) throws Exception {
        KieuPhong KieuPhong = getKieuPhongById(id);
        if(request.getTenKieuPhong() != null)
            KieuPhong.setTenKieuPhong(request.getTenKieuPhong());
        if(request.getMoTa() != null)
            KieuPhong.setMoTa(request.getMoTa());
        return repository.save(KieuPhong);
    }

    @Override
    public void deleteKieuPhong(Integer id) throws Exception {
        Optional<KieuPhong> KieuPhongOptional = repository.findById(id);
        if(KieuPhongOptional.isEmpty()){
            throw new Exception("KieuPhong not found");
        }
        repository.deleteById(id);
    }
}