package com.demo.MiniHotel.modules.ctkm.implement;

import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.ChuongTrinhKhuyenMai;
import com.demo.MiniHotel.model.DoanhThuTheoNgay;
import com.demo.MiniHotel.model.NhanVien;
import com.demo.MiniHotel.modules.ctkm.dto.ChuongTrinhKhuyenMaiRequest;
import com.demo.MiniHotel.modules.ctkm.dto.ChuongTrinhKhuyenMaiResponse;
import com.demo.MiniHotel.modules.ctkm.service.IChuongTrinhKhuyenMaiService;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import com.demo.MiniHotel.repository.ChuongTrinhKhuyenMaiRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChuongTrinhKhuyenMaiImplement implements IChuongTrinhKhuyenMaiService {
    ChuongTrinhKhuyenMaiRepository repository;

    INhanVienService nhanVienService;
    @PreAuthorize("hasRole('STAFF')")
    @Override
    public ChuongTrinhKhuyenMai themChuongTrinhKhuyenMai(ChuongTrinhKhuyenMaiRequest request) throws Exception {
        if(request.getNgayBatDau().isAfter(request.getNgayKetThuc()))
            throw new AppException(ErrorCode.THOIGIAN_NOT_VALID);
        if(request.getNgayBatDau().isBefore(LocalDate.now()))
            throw new AppException(ErrorCode.THOIGIAN_KM_INVALID);

        ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = new ChuongTrinhKhuyenMai();
        chuongTrinhKhuyenMai.setMoTa(request.getMoTa());
        chuongTrinhKhuyenMai.setNgayBatDau(request.getNgayBatDau());
        chuongTrinhKhuyenMai.setNgayKetThuc(request.getNgayKetThuc());

        NhanVien nhanVien = nhanVienService.getNhanVienByToken();
        chuongTrinhKhuyenMai.setNhanVien(nhanVien);

        return repository.save(chuongTrinhKhuyenMai);
    }

    @PreAuthorize("hasRole('STAFF')")
    @Override
    public List<ChuongTrinhKhuyenMai> getAllChuongTrinhKhuyenMai() {
        return repository.findAll();
    }

    @PreAuthorize("hasRole('STAFF')")
    @Override
    public ChuongTrinhKhuyenMai getChuongTrinhKhuyenMaiById(Integer id) throws Exception {
        Optional<ChuongTrinhKhuyenMai> ChuongTrinhKhuyenMaiOptional = repository.findById(id);
        if(ChuongTrinhKhuyenMaiOptional.isEmpty()){
            throw new Exception("ChuongTrinhKhuyenMai not found");
        }
        return ChuongTrinhKhuyenMaiOptional.get();
    }
    @PreAuthorize("hasRole('STAFF')")
    @Override
    public ChuongTrinhKhuyenMai updateChuongTrinhKhuyenMai(ChuongTrinhKhuyenMaiRequest request, Integer id) throws Exception {
        ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = getChuongTrinhKhuyenMaiById(id);

        chuongTrinhKhuyenMai.setMoTa(request.getMoTa());
        chuongTrinhKhuyenMai.setNgayBatDau(request.getNgayBatDau());
        chuongTrinhKhuyenMai.setNgayKetThuc(request.getNgayKetThuc());

        return repository.save(chuongTrinhKhuyenMai);
    }
    @PreAuthorize("hasRole('STAFF')")
    @Override
    public void deleteChuongTrinhKhuyenMai(Integer id) throws Exception {
        Optional<ChuongTrinhKhuyenMai> ChuongTrinhKhuyenMaiOptional = repository.findById(id);
        if(ChuongTrinhKhuyenMaiOptional.isEmpty()){
            throw new Exception("ChuongTrinhKhuyenMai not found");
        }
        repository.deleteById(id);
    }
    @PreAuthorize("hasRole('STAFF')")
    @Override
    public ChuongTrinhKhuyenMaiResponse getChuongTrinhKhuyenMaiResponseById(Integer id) throws Exception {
        Optional<ChuongTrinhKhuyenMai> ChuongTrinhKhuyenMaiOptional = repository.findById(id);
        if(ChuongTrinhKhuyenMaiOptional.isEmpty()){
            throw new Exception("ChuongTrinhKhuyenMai not found");
        }
        return convertChuongTrinhKhuyenMaiResponse(ChuongTrinhKhuyenMaiOptional.get());
    }

    @PreAuthorize("hasRole('STAFF')")
    @Override
    public List<ChuongTrinhKhuyenMaiResponse> getAllChuongTrinhKhuyenMaiResponse() {
        List<ChuongTrinhKhuyenMai> responses = repository.findAll();
        responses.sort(new Comparator<ChuongTrinhKhuyenMai>() {
            @Override
            public int compare(ChuongTrinhKhuyenMai o1, ChuongTrinhKhuyenMai o2) {
                return o1.getNgayKetThuc().isBefore(o2.getNgayKetThuc()) ? 1 : -1;
            }
        });
        return responses.stream()
                .map(this::convertChuongTrinhKhuyenMaiResponse).toList();
    }

    public ChuongTrinhKhuyenMaiResponse convertChuongTrinhKhuyenMaiResponse(ChuongTrinhKhuyenMai chuongTrinhKhuyenMai){
        return ChuongTrinhKhuyenMaiResponse.builder()
                .idKhuyenMai(chuongTrinhKhuyenMai.getIdKhuyenMai())
                .moTa(chuongTrinhKhuyenMai.getMoTa())
                .ngayBatDau(chuongTrinhKhuyenMai.getNgayBatDau())
                .ngayKetThuc(chuongTrinhKhuyenMai.getNgayKetThuc())
                .tenNhanVien(chuongTrinhKhuyenMai.getNhanVien().getHoTen())
                .build();
    }
}
