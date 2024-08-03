package com.demo.MiniHotel.modules.nhomquyen.implement;

import com.demo.MiniHotel.modules.nhomquyen.dto.NhomQuyenRequest;
import com.demo.MiniHotel.model.NhomQuyen;
import com.demo.MiniHotel.repository.NhomQuyenRepository;
import com.demo.MiniHotel.modules.nhomquyen.service.INhomQuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NhomQuyenImplement implements INhomQuyenService {
    private final NhomQuyenRepository repository;
    @Override
    public NhomQuyen addNewNhomQuyen(NhomQuyenRequest request) {
        NhomQuyen nhomQuyen = new NhomQuyen();
        nhomQuyen.setTenNhomQuyen(request.getTenNhomQuyen());

        return repository.save(nhomQuyen);
    }

    @Override
    public List<NhomQuyen> getAllNhomQuyen() {
        return repository.findAll();
    }

    @Override
    public NhomQuyen getNhomQuyenById(Integer id) throws Exception {
        Optional<NhomQuyen> nhomQuyenOptional = repository.findById(id);
        if(nhomQuyenOptional.isEmpty()){
            throw new Exception("NhomQuyen not found");
        }
        return nhomQuyenOptional.get();
    }

    @Override
    public NhomQuyen updateNhomQuyen(NhomQuyenRequest request, Integer id) throws Exception {
        NhomQuyen nhomQuyen = getNhomQuyenById(id);
        if(request.getTenNhomQuyen() != null)
            nhomQuyen.setTenNhomQuyen(request.getTenNhomQuyen());
        return repository.save(nhomQuyen);
    }

    @Override
    public void deleteNhomQuyen(Integer id) throws Exception {
        Optional<NhomQuyen> nhomQuyenOptional = repository.findById(id);
        if(nhomQuyenOptional.isEmpty()){
            throw new Exception("NhomQuyen not found");
        }
        repository.deleteById(id);
    }
}
