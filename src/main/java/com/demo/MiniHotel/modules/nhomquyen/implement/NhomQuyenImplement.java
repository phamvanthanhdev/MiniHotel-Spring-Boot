package com.demo.MiniHotel.modules.nhomquyen.implement;

import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.modules.nhomquyen.dto.NhomQuyenRequest;
import com.demo.MiniHotel.model.NhomQuyen;
import com.demo.MiniHotel.modules.nhomquyen.dto.NhomQuyenResponse;
import com.demo.MiniHotel.repository.NhomQuyenRepository;
import com.demo.MiniHotel.modules.nhomquyen.service.INhomQuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NhomQuyenImplement implements INhomQuyenService {
    private final NhomQuyenRepository repository;
    @Override
    public NhomQuyen addNewNhomQuyen(NhomQuyenRequest request) {
        if(repository.existsByTenNhomQuyen(request.getTenNhomQuyen()))
            throw new AppException(ErrorCode.TENNHOMQUYEN_EXISTED);

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
    public NhomQuyenResponse updateNhomQuyen(NhomQuyenRequest request, Integer id) throws Exception {
        NhomQuyen nhomQuyen = getNhomQuyenById(id);
        if(nhomQuyen.getTenNhomQuyen().equals(request.getTenNhomQuyen()) && repository.existsByTenNhomQuyen(request.getTenNhomQuyen()))
            throw new AppException(ErrorCode.TENNHOMQUYEN_EXISTED);

        if(nhomQuyen.getTenNhomQuyen().trim().equals("ADMIN")
                || nhomQuyen.getTenNhomQuyen().trim().equals("STAFF")
                || nhomQuyen.getTenNhomQuyen().trim().equals("USER")
        ){
            throw new AppException(ErrorCode.TENNHOMQUYEN_QUANTRONG);
        }

        nhomQuyen.setTenNhomQuyen(request.getTenNhomQuyen());
        return convertNhomQuyenResponse(repository.save(nhomQuyen));
    }

    @Override
    public void deleteNhomQuyen(Integer id) throws Exception {
        NhomQuyen nhomQuyen = getNhomQuyenById(id);

        if(nhomQuyen.getTaiKhoans().size() > 0)
            throw new AppException(ErrorCode.NHOMQUYEN_DANGSUDUNG);

        if(nhomQuyen.getTenNhomQuyen().trim().equals("ADMIN")
        || nhomQuyen.getTenNhomQuyen().trim().equals("STAFF")
                || nhomQuyen.getTenNhomQuyen().trim().equals("USER")
        ){
            throw new AppException(ErrorCode.TENNHOMQUYEN_QUANTRONG);
        }
        repository.deleteById(id);
    }

    @Override
    public List<NhomQuyenResponse> getAllNhomQuyenResponse() {
        List<NhomQuyen> nhomQuyens = getAllNhomQuyen();
        return nhomQuyens.stream().map(this::convertNhomQuyenResponse).collect(Collectors.toList());
    }

    @Override
    public NhomQuyenResponse getNhomQuyenResponseById(Integer id) throws Exception {
        NhomQuyen nhomQuyen = getNhomQuyenById(id);
        return convertNhomQuyenResponse(nhomQuyen);
    }

    public NhomQuyenResponse convertNhomQuyenResponse(NhomQuyen nhomQuyen){
        return NhomQuyenResponse.builder()
                .idNhomQuyen(nhomQuyen.getIdNhomQuyen())
                .tenNhomQuyen(nhomQuyen.getTenNhomQuyen())
                .build();
    }
}
