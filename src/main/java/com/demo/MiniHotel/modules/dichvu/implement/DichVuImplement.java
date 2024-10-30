package com.demo.MiniHotel.modules.dichvu.implement;

import com.demo.MiniHotel.embedded.IdChiTietThayDoiGiaDichVuEmb;
import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.dichvu.dto.ChiTietGiaDichVuRequest;
import com.demo.MiniHotel.modules.dichvu.dto.ChiTietGiaDichVuResponse;
import com.demo.MiniHotel.modules.dichvu.dto.DichVuRequest;
import com.demo.MiniHotel.modules.dichvu.dto.DichVuResponse;
import com.demo.MiniHotel.modules.dichvu.service.IDichVuService;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import com.demo.MiniHotel.modules.phuthu.dto.ChiTietGiaPhuThuResponse;
import com.demo.MiniHotel.repository.ChiTietThayDoiGiaDichVuRepository;
import com.demo.MiniHotel.repository.DichVuRepository;
import com.demo.MiniHotel.repository.ThongTinDichVuRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DichVuImplement implements IDichVuService {
    DichVuRepository repository;
    ThongTinDichVuRepository thongTinDichVuRepository;
    ChiTietThayDoiGiaDichVuRepository thayDoiGiaDichVuRepository;

    INhanVienService nhanVienService;

    @Override
    public DichVuResponse addNewDichVu(DichVuRequest request) throws Exception {
        DichVu dichVu = new DichVu();
        dichVu.setTenDichVu(request.getTenDichVu());
        DichVu dichVuMoi = repository.save(dichVu);

        // Thêm giá gốc ban đầu
        LocalDate ngayHienTai = LocalDate.now();
        ChiTietGiaDichVuRequest giaDichVuRequest = ChiTietGiaDichVuRequest.builder()
                .idDichVu(dichVuMoi.getIdDichVu())
                .giaCapNhat(request.getDonGia())
                .ngayApDung(ngayHienTai)
                .build();
        themChiTietThayDoiGiaDichVu(giaDichVuRequest);

        return convertDichVuResponse(getThongTinDichVuResponseById(dichVuMoi.getIdDichVu()));
    }

    @Override
    public List<DichVu> getAllDichVu() {
        return repository.findAll();
    }

    @Override
    public DichVu getDichVuById(Integer id) throws Exception {
        Optional<DichVu> DichVuOptional = repository.findById(id);
        if(DichVuOptional.isEmpty()){
            throw new Exception("DichVu not found.");
        }

        return DichVuOptional.get();
    }

    @Override
    public DichVuResponse updateDichVu(DichVuRequest request, Integer id) throws Exception {
        DichVu dichVu = getDichVuById(id);
        dichVu.setTenDichVu(request.getTenDichVu());

        repository.save(dichVu);

        return convertDichVuResponse(getThongTinDichVuResponseById(id));
    }

    @Override
    public void deleteDichVu(Integer id) throws Exception {
        Optional<DichVu> DichVuOptional = repository.findById(id);
        if(DichVuOptional.isEmpty()){
            throw new Exception("DichVu not found.");
        }

        repository.deleteById(id);
    }

    @Override
    public ThongTinDichVu getThongTinDichVuResponseById(int idDichVu) {
        ThongTinDichVu thongTinDichVu = thongTinDichVuRepository.findById(idDichVu).orElseThrow(
                () -> new AppException(ErrorCode.DICHVU_NOT_FOUND)
        );
        return thongTinDichVu;
    }

    @Override
    public List<DichVuResponse> getAllDichVuResponse() {
        List<ThongTinDichVu> thongTinDichVus = getAllThongTinDichVu();
        return thongTinDichVus.stream().map(this::convertDichVuResponse).toList();
    }

    @Override
    public List<ThongTinDichVu> getAllThongTinDichVu() {
        return thongTinDichVuRepository.findAll();
    }

    @Override
    public List<ChiTietGiaDichVuResponse> getChiTietThayDoiGiaDichVu() {
        List<ChiTietThayDoiGiaDichVu> thayDoiGiaDichVus = thayDoiGiaDichVuRepository.findAll();
        Collections.sort(thayDoiGiaDichVus, new Comparator<ChiTietThayDoiGiaDichVu>() {
            @Override
            public int compare(ChiTietThayDoiGiaDichVu o1, ChiTietThayDoiGiaDichVu o2) {
                return o1.getNgayApDung().isBefore(o2.getNgayApDung()) ? 1 : -1;
            }
        });

        return thayDoiGiaDichVus.stream().map(this::convertChiTietGiaDichVuResponse).toList();
    }

    @Override
    public ChiTietGiaDichVuResponse getChiTietThayDoiGiaDichVuById(int idDichVu, int idNhanVien, LocalDate ngayCapNhat) {
        ChiTietThayDoiGiaDichVu chiTietThayDoiGiaDichVu = thayDoiGiaDichVuRepository.findById(
                new IdChiTietThayDoiGiaDichVuEmb(idDichVu, idNhanVien, ngayCapNhat))
                .orElseThrow(() -> new AppException(ErrorCode.CHITIETGIA_NOT_FOUND));
        return convertChiTietGiaDichVuResponse(chiTietThayDoiGiaDichVu);
    }

    @Override
    public ChiTietThayDoiGiaDichVu themChiTietThayDoiGiaDichVu(ChiTietGiaDichVuRequest request) throws Exception {
        NhanVien nhanVien = nhanVienService.getNhanVienByToken();
        LocalDate ngayCapNhat = LocalDate.now();

        if(request.getNgayApDung().isBefore(ngayCapNhat))
            throw new AppException(ErrorCode.THOIGIAN_NOT_VALID);

        if(thayDoiGiaDichVuRepository.existsById(
                new IdChiTietThayDoiGiaDichVuEmb(request.getIdDichVu(), nhanVien.getIdNhanVien(), ngayCapNhat))){
            throw new AppException(ErrorCode.CHITIETGIA_EXISTED);
        }

        if(thayDoiGiaDichVuRepository
                .existsByIdChiTietThayDoiGiaDichVuEmb_IdDichVuAndNgayApDung
                        (request.getIdDichVu(), request.getNgayApDung())){
            throw new AppException(ErrorCode.CHITIETGIA_EXISTED_2);
        }

        ChiTietThayDoiGiaDichVu chiTietThayDoiGiaDichVu = new ChiTietThayDoiGiaDichVu();
        chiTietThayDoiGiaDichVu.setIdChiTietThayDoiGiaDichVuEmb(
                new IdChiTietThayDoiGiaDichVuEmb(request.getIdDichVu(), nhanVien.getIdNhanVien(), ngayCapNhat)
        );
        chiTietThayDoiGiaDichVu.setNhanVien(nhanVien);
        chiTietThayDoiGiaDichVu.setDichVu(getDichVuById(request.getIdDichVu()));
        chiTietThayDoiGiaDichVu.setGiaCapNhat(request.getGiaCapNhat());
        chiTietThayDoiGiaDichVu.setNgayApDung(request.getNgayApDung());

        return thayDoiGiaDichVuRepository.save(chiTietThayDoiGiaDichVu);
    }

    @Override
    public ChiTietThayDoiGiaDichVu capNhatChiTietThayDoiGiaDichVu(int idDichVu, int idNhanVien, LocalDate ngayCapNhat, ChiTietGiaDichVuRequest request) {
        LocalDate ngayHienTai = LocalDate.now();

        if(request.getNgayApDung().isBefore(ngayHienTai))
            throw new AppException(ErrorCode.THOIGIAN_NOT_VALID);

        ChiTietThayDoiGiaDichVu chiTietThayDoiGiaDichVu =
                thayDoiGiaDichVuRepository.findById(
                        new IdChiTietThayDoiGiaDichVuEmb(idDichVu, idNhanVien, ngayCapNhat))
                        .orElseThrow(() -> new AppException(ErrorCode.CHITIETGIA_NOT_FOUND));

        if(!request.getNgayApDung().equals(chiTietThayDoiGiaDichVu.getNgayApDung())) {
            if (thayDoiGiaDichVuRepository
                    .existsByIdChiTietThayDoiGiaDichVuEmb_IdDichVuAndNgayApDung
                            (request.getIdDichVu(), request.getNgayApDung())) {
                throw new AppException(ErrorCode.CHITIETGIA_EXISTED_2);
            }
        }

        chiTietThayDoiGiaDichVu.setGiaCapNhat(request.getGiaCapNhat());
        chiTietThayDoiGiaDichVu.setNgayApDung(request.getNgayApDung());

        return thayDoiGiaDichVuRepository.save(chiTietThayDoiGiaDichVu);
    }

    @Override
    public void xoaChiTietThayDoiGiaDichVu(int idDichVu, int idNhanVien, LocalDate ngayCapNhat) {
        List<ChiTietThayDoiGiaDichVu> chiTietThayDoiGiaDichVus =
                thayDoiGiaDichVuRepository.findByIdChiTietThayDoiGiaDichVuEmb_IdDichVu(idDichVu);
        if(chiTietThayDoiGiaDichVus.size() <= 1)
            throw new AppException(ErrorCode.CHITIETGIA_FINAL);

        if(!thayDoiGiaDichVuRepository.existsById(new IdChiTietThayDoiGiaDichVuEmb(idDichVu, idNhanVien, ngayCapNhat)))
            throw new AppException(ErrorCode.CHITIETGIA_NOT_FOUND);

        thayDoiGiaDichVuRepository.deleteById(new IdChiTietThayDoiGiaDichVuEmb(idDichVu, idNhanVien, ngayCapNhat));
    }

    @Override
    public DichVuResponse getDichVuResponseById(Integer idDichVu) throws Exception {
        return convertDichVuResponse(getThongTinDichVuResponseById(idDichVu));
    }


    public DichVuResponse convertDichVuResponse(ThongTinDichVu thongTinDichVu){
        return DichVuResponse.builder()
                .idDichVu(thongTinDichVu.getIdDichVu())
                .tenDichVu(thongTinDichVu.getTenDichVu())
                .donGia(thongTinDichVu.getGiaCapNhat())
                .build();
    }

    public ChiTietGiaDichVuResponse convertChiTietGiaDichVuResponse(ChiTietThayDoiGiaDichVu thayDoiGiaDichVu){
        return ChiTietGiaDichVuResponse.builder()
                .idDichVu(thayDoiGiaDichVu.getIdChiTietThayDoiGiaDichVuEmb().getIdDichVu())
                .tenDichVu(thayDoiGiaDichVu.getDichVu().getTenDichVu())
                .idNhanVien(thayDoiGiaDichVu.getIdChiTietThayDoiGiaDichVuEmb().getIdNhanVien())
                .tenNhanVien(thayDoiGiaDichVu.getNhanVien().getHoTen())
                .giaCapNhat(thayDoiGiaDichVu.getGiaCapNhat())
                .ngayApDung(thayDoiGiaDichVu.getNgayApDung())
                .ngayCapNhat(thayDoiGiaDichVu.getIdChiTietThayDoiGiaDichVuEmb().getNgayCapNhat())
                .build();
    }
}
