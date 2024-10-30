package com.demo.MiniHotel.modules.phuthu.implement;

import com.demo.MiniHotel.embedded.IdChiTietThayDoiGiaPhuThuEmb;
import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import com.demo.MiniHotel.modules.phuthu.dto.ChiTietGiaPhuThuRequest;
import com.demo.MiniHotel.modules.phuthu.dto.ChiTietGiaPhuThuResponse;
import com.demo.MiniHotel.modules.phuthu.dto.PhuThuRequest;
import com.demo.MiniHotel.modules.phuthu.dto.PhuThuResponse;
import com.demo.MiniHotel.modules.phuthu.service.IPhuThuService;
import com.demo.MiniHotel.repository.ChiTietThayDoiGiaPhuThuRepository;
import com.demo.MiniHotel.repository.PhuThuRepository;
import com.demo.MiniHotel.repository.ThongTinPhuThuRepository;
import com.demo.MiniHotel.repository.ThongTinPhuThuRepository;
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
public class PhuThuImplement implements IPhuThuService {
    PhuThuRepository repository;
    ThongTinPhuThuRepository thongTinPhuThuRepository;
    ChiTietThayDoiGiaPhuThuRepository thayDoiGiaPhuThuRepository;
    INhanVienService nhanVienService;
    @Override
    public PhuThuResponse addNewPhuThu(PhuThuRequest request) throws Exception {
        PhuThu phuThu = new PhuThu();
        phuThu.setNoiDung(request.getNoiDung());
        PhuThu phuThuMoi = repository.save(phuThu);

        // Thêm giá gốc ban đầu
        LocalDate ngayHienTai = LocalDate.now();
        ChiTietGiaPhuThuRequest giaPhuThuRequest = ChiTietGiaPhuThuRequest.builder()
                .idPhuThu(phuThuMoi.getIdPhuThu())
                .giaCapNhat(request.getDonGia())
                .ngayApDung(ngayHienTai)
                .build();
        themChiTietThayDoiGiaPhuThu(giaPhuThuRequest);

        return convertPhuThuResponse(getThongTinPhuThuResponseById(phuThuMoi.getIdPhuThu()));
    }

    @Override
    public List<PhuThu> getAllPhuThu() {
        return repository.findAll();
    }

    @Override
    public PhuThu getPhuThuById(Integer id) throws Exception {
        Optional<PhuThu> PhuThuOptional = repository.findById(id);
        if(PhuThuOptional.isEmpty()){
            throw new Exception("PhuThu not found.");
        }

        return PhuThuOptional.get();
    }

    @Override
    public PhuThuResponse updatePhuThu(PhuThuRequest request, Integer id) throws Exception {
        PhuThu PhuThu = getPhuThuById(id);
        PhuThu.setNoiDung(request.getNoiDung());

        repository.save(PhuThu);

        return convertPhuThuResponse(getThongTinPhuThuResponseById(id));
    }

    @Override
    public void deletePhuThu(Integer id) throws Exception {
        Optional<PhuThu> PhuThuOptional = repository.findById(id);
        if(PhuThuOptional.isEmpty()){
            throw new Exception("PhuThu not found.");
        }

        repository.deleteById(id);
    }

    @Override
    public ThongTinPhuThu getThongTinPhuThuResponseById(int idPhuThu) {
        ThongTinPhuThu thongTinPhuThu = thongTinPhuThuRepository.findById(idPhuThu).orElseThrow(
                () -> new AppException(ErrorCode.PHUTHU_NOT_FOUND)
        );
        return thongTinPhuThu;
    }

    @Override
    public List<PhuThuResponse> getAllPhuThuResponse() {
        List<ThongTinPhuThu> thongTinPhuThus = getAllThongTinPhuThu();
        return thongTinPhuThus.stream().map(this::convertPhuThuResponse).toList();
    }

    @Override
    public List<ThongTinPhuThu> getAllThongTinPhuThu() {
        return thongTinPhuThuRepository.findAll();
    }

    @Override
    public List<ChiTietGiaPhuThuResponse> getChiTietThayDoiGiaPhuThu() {
        List<ChiTietThayDoiGiaPhuThu> thayDoiGiaPhuThus = thayDoiGiaPhuThuRepository.findAll();
        Collections.sort(thayDoiGiaPhuThus, new Comparator<ChiTietThayDoiGiaPhuThu>() {
            @Override
            public int compare(ChiTietThayDoiGiaPhuThu o1, ChiTietThayDoiGiaPhuThu o2) {
                return o1.getNgayApDung().isBefore(o2.getNgayApDung()) ? 1 : -1;
            }
        });

        return thayDoiGiaPhuThus.stream().map(this::convertChiTietGiaPhuThuResponse).toList();
    }

    @Override
    public ChiTietGiaPhuThuResponse getChiTietThayDoiGiaPhuThuById(int idPhuThu, int idNhanVien, LocalDate ngayCapNhat) {
        ChiTietThayDoiGiaPhuThu chiTietThayDoiGiaPhuThu = thayDoiGiaPhuThuRepository.findById(
                        new IdChiTietThayDoiGiaPhuThuEmb(idPhuThu, idNhanVien, ngayCapNhat))
                .orElseThrow(() -> new AppException(ErrorCode.CHITIETGIA_NOT_FOUND));
        return convertChiTietGiaPhuThuResponse(chiTietThayDoiGiaPhuThu);
    }

    @Override
    public ChiTietThayDoiGiaPhuThu themChiTietThayDoiGiaPhuThu(ChiTietGiaPhuThuRequest request) throws Exception {
        NhanVien nhanVien = nhanVienService.getNhanVienByToken();
        LocalDate ngayCapNhat = LocalDate.now();

        if(request.getNgayApDung().isBefore(ngayCapNhat))
            throw new AppException(ErrorCode.THOIGIAN_NOT_VALID);

        if(thayDoiGiaPhuThuRepository.existsById(
                new IdChiTietThayDoiGiaPhuThuEmb(request.getIdPhuThu(), nhanVien.getIdNhanVien(), ngayCapNhat))){
            throw new AppException(ErrorCode.CHITIETGIA_EXISTED);
        }

        if(thayDoiGiaPhuThuRepository
                .existsByIdChiTietThayDoiGiaPhuThuEmb_IdPhuThuAndNgayApDung
                        (request.getIdPhuThu(), request.getNgayApDung())){
            throw new AppException(ErrorCode.CHITIETGIA_EXISTED_2);
        }

        ChiTietThayDoiGiaPhuThu chiTietThayDoiGiaPhuThu = new ChiTietThayDoiGiaPhuThu();
        chiTietThayDoiGiaPhuThu.setIdChiTietThayDoiGiaPhuThuEmb(
                new IdChiTietThayDoiGiaPhuThuEmb(request.getIdPhuThu(), nhanVien.getIdNhanVien(), ngayCapNhat)
        );
        chiTietThayDoiGiaPhuThu.setNhanVien(nhanVien);
        chiTietThayDoiGiaPhuThu.setPhuThu(getPhuThuById(request.getIdPhuThu()));
        chiTietThayDoiGiaPhuThu.setGiaCapNhat(request.getGiaCapNhat());
        chiTietThayDoiGiaPhuThu.setNgayApDung(request.getNgayApDung());

        return thayDoiGiaPhuThuRepository.save(chiTietThayDoiGiaPhuThu);
    }

    @Override
    public ChiTietThayDoiGiaPhuThu capNhatChiTietThayDoiGiaPhuThu(int idPhuThu, int idNhanVien, LocalDate ngayCapNhat, ChiTietGiaPhuThuRequest request) {
        LocalDate ngayHienTai = LocalDate.now();

        if(request.getNgayApDung().isBefore(ngayHienTai))
            throw new AppException(ErrorCode.THOIGIAN_NOT_VALID);

        ChiTietThayDoiGiaPhuThu chiTietThayDoiGiaPhuThu =
                thayDoiGiaPhuThuRepository.findById(
                                new IdChiTietThayDoiGiaPhuThuEmb(idPhuThu, idNhanVien, ngayCapNhat))
                        .orElseThrow(() -> new AppException(ErrorCode.CHITIETGIA_NOT_FOUND));

        if(!request.getNgayApDung().equals(chiTietThayDoiGiaPhuThu.getNgayApDung())) {
            if (thayDoiGiaPhuThuRepository
                    .existsByIdChiTietThayDoiGiaPhuThuEmb_IdPhuThuAndNgayApDung
                            (request.getIdPhuThu(), request.getNgayApDung())) {
                throw new AppException(ErrorCode.CHITIETGIA_EXISTED_2);
            }
        }

        chiTietThayDoiGiaPhuThu.setGiaCapNhat(request.getGiaCapNhat());
        chiTietThayDoiGiaPhuThu.setNgayApDung(request.getNgayApDung());

        return thayDoiGiaPhuThuRepository.save(chiTietThayDoiGiaPhuThu);
    }

    @Override
    public void xoaChiTietThayDoiGiaPhuThu(int idPhuThu, int idNhanVien, LocalDate ngayCapNhat) {
        List<ChiTietThayDoiGiaPhuThu> chiTietThayDoiGiaPhuThus =
                thayDoiGiaPhuThuRepository.findByIdChiTietThayDoiGiaPhuThuEmb_IdPhuThu(idPhuThu);
        if(chiTietThayDoiGiaPhuThus.size() <= 1)
            throw new AppException(ErrorCode.CHITIETGIA_FINAL);

        if(!thayDoiGiaPhuThuRepository.existsById(new IdChiTietThayDoiGiaPhuThuEmb(idPhuThu, idNhanVien, ngayCapNhat)))
            throw new AppException(ErrorCode.CHITIETGIA_NOT_FOUND);

        thayDoiGiaPhuThuRepository.deleteById(new IdChiTietThayDoiGiaPhuThuEmb(idPhuThu, idNhanVien, ngayCapNhat));
    }

    public PhuThuResponse convertPhuThuResponse(ThongTinPhuThu thongTinPhuThu){
        return PhuThuResponse.builder()
                .idPhuThu(thongTinPhuThu.getIdPhuThu())
                .noiDung(thongTinPhuThu.getNoiDung())
                .donGia(thongTinPhuThu.getGiaCapNhat())
                .build();


    }

    @Override
    public PhuThuResponse getPhuThuResponseById(Integer idPhuThu) throws Exception {
        return convertPhuThuResponse(getThongTinPhuThuResponseById(idPhuThu));
    }

    public ChiTietGiaPhuThuResponse convertChiTietGiaPhuThuResponse(ChiTietThayDoiGiaPhuThu thayDoiGiaPhuThu){
        return ChiTietGiaPhuThuResponse.builder()
                .idPhuThu(thayDoiGiaPhuThu.getIdChiTietThayDoiGiaPhuThuEmb().getIdPhuThu())
                .noiDung(thayDoiGiaPhuThu.getPhuThu().getNoiDung())
                .idNhanVien(thayDoiGiaPhuThu.getIdChiTietThayDoiGiaPhuThuEmb().getIdNhanVien())
                .tenNhanVien(thayDoiGiaPhuThu.getNhanVien().getHoTen())
                .giaCapNhat(thayDoiGiaPhuThu.getGiaCapNhat())
                .ngayApDung(thayDoiGiaPhuThu.getNgayApDung())
                .ngayCapNhat(thayDoiGiaPhuThu.getIdChiTietThayDoiGiaPhuThuEmb().getNgayCapNhat())
                .build();
    }
}
