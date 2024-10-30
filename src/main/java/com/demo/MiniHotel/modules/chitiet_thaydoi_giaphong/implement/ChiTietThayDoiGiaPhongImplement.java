package com.demo.MiniHotel.modules.chitiet_thaydoi_giaphong.implement;

import com.demo.MiniHotel.embedded.IdChiTietThayDoiGiaPhongEmb;
import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.ChiTietThayDoiGiaPhong;
import com.demo.MiniHotel.model.HangPhong;
import com.demo.MiniHotel.model.NhanVien;
import com.demo.MiniHotel.model.ThongTinHangPhong;
import com.demo.MiniHotel.modules.chitiet_thaydoi_giaphong.dto.ChiTietGiaPhongRequest;
import com.demo.MiniHotel.modules.chitiet_thaydoi_giaphong.dto.ChiTietGiaPhongResponse;
import com.demo.MiniHotel.modules.chitiet_thaydoi_giaphong.service.IChiTietThayDoiGiaPhongService;
import com.demo.MiniHotel.modules.hangphong.service.IHangPhongService;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import com.demo.MiniHotel.repository.ChiTietThayDoiGiaPhongRepository;
import com.demo.MiniHotel.repository.HangPhongRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
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
@Slf4j
public class ChiTietThayDoiGiaPhongImplement implements IChiTietThayDoiGiaPhongService {
    ChiTietThayDoiGiaPhongRepository repository;

//    IHangPhongService hangPhongService;
    INhanVienService nhanVienService;
    HangPhongRepository hangPhongRepository;

    IThongTinHangPhongService thongTinHangPhongService;


    @PreAuthorize("hasRole('STAFF')")
    @Override
    public ChiTietThayDoiGiaPhong themChiTietThayDoiGiaPhong(ChiTietGiaPhongRequest request) throws Exception {
        LocalDate ngayCapNhat = LocalDate.now();
        NhanVien nhanVien = nhanVienService.getNhanVienByToken();
        Optional<ChiTietThayDoiGiaPhong> chiTietGiaPhongOptional =
                repository.findById(new IdChiTietThayDoiGiaPhongEmb(request.getIdHangPhong(), nhanVien.getIdNhanVien(), ngayCapNhat));
        if(chiTietGiaPhongOptional.isPresent()){
            if(chiTietGiaPhongOptional.get().getIdChiTietThayDoiGiaPhongEmb().getNgayCapNhat().equals(ngayCapNhat))
                throw new AppException(ErrorCode.GIAPHONG_EXISTED);
        }

        ChiTietThayDoiGiaPhong chiTietThayDoiGiaPhong = new ChiTietThayDoiGiaPhong();
        chiTietThayDoiGiaPhong.setIdChiTietThayDoiGiaPhongEmb(
                new IdChiTietThayDoiGiaPhongEmb(request.getIdHangPhong(), nhanVien.getIdNhanVien(), ngayCapNhat));

        HangPhong hangPhong = hangPhongRepository.findByIdHangPhong(
                request.getIdHangPhong()).orElseThrow(() -> new Exception("HangPhong not found."));

        chiTietThayDoiGiaPhong.setHangPhong(hangPhong);
        chiTietThayDoiGiaPhong.setNhanVien(nhanVien);
        chiTietThayDoiGiaPhong.setGiaCapNhat(request.getGiaCapNhat());
        chiTietThayDoiGiaPhong.setNgayApDung(request.getNgayApDung());
//        chiTietThayDoiGiaPhong.setNgayCapNhat(ngayCapNhat);

        return repository.save(chiTietThayDoiGiaPhong);
    }

    @PreAuthorize("hasRole('STAFF')")
    @Override
    public List<ChiTietThayDoiGiaPhong> getAllChiTietThayDoiGiaPhong() {
        return repository.findAll();
    }

    @PreAuthorize("hasRole('STAFF')")
    @Override
    public ChiTietThayDoiGiaPhong getChiTietThayDoiGiaPhongById(int idHangPhong, int idNhanVien,  LocalDate ngayCapNhat) throws Exception {
        Optional<ChiTietThayDoiGiaPhong> ChiTietThayDoiGiaPhongOptional = repository.findById(new IdChiTietThayDoiGiaPhongEmb(idHangPhong, idNhanVien, ngayCapNhat));
        if(ChiTietThayDoiGiaPhongOptional.isEmpty()){
            throw new Exception("ChiTietThayDoiGiaPhong not found");
        }
        return ChiTietThayDoiGiaPhongOptional.get();
    }

    @PreAuthorize("hasRole('STAFF')")
    @Override
    public ChiTietGiaPhongResponse updateChiTietThayDoiGiaPhong(ChiTietGiaPhongRequest request,int idHangPhong,  int idNhanVien, LocalDate ngayCapNhat) throws Exception {
        NhanVien nhanVien = nhanVienService.getNhanVienByToken();
        ChiTietThayDoiGiaPhong chiTietThayDoiGiaPhong = getChiTietThayDoiGiaPhongById(idHangPhong, idNhanVien, ngayCapNhat);

        // Nếu nhân viên cập nhật không phải là nhân viên tạo thì không cho phép
        if(!nhanVien.getIdNhanVien().equals(chiTietThayDoiGiaPhong.getNhanVien().getIdNhanVien()))
            throw new AppException(ErrorCode.NHANVIEN_AUTHORIZATION);

        chiTietThayDoiGiaPhong.setGiaCapNhat(request.getGiaCapNhat());
        chiTietThayDoiGiaPhong.setNgayApDung(request.getNgayApDung());

        return convertChiTietThayDoiGiaPhongResponse(repository.save(chiTietThayDoiGiaPhong));
    }
    @PreAuthorize("hasRole('STAFF')")
    @Override
    public void deleteChiTietThayDoiGiaPhong(int idHangPhong, int idNhanVien, LocalDate ngayCapNhat) throws Exception {
        Optional<ChiTietThayDoiGiaPhong> ChiTietThayDoiGiaPhongOptional = repository.findById(new IdChiTietThayDoiGiaPhongEmb(idHangPhong, idNhanVien, ngayCapNhat));
        if(ChiTietThayDoiGiaPhongOptional.isEmpty()){
            throw new Exception("ChiTietThayDoiGiaPhong not found");
        }
        repository.deleteById(new IdChiTietThayDoiGiaPhongEmb(idHangPhong, idNhanVien, ngayCapNhat));
    }
    @PreAuthorize("hasRole('STAFF')")
    @Override
    public ChiTietGiaPhongResponse getChiTietThayDoiGiaPhongResponseById(int idHangPhong, int idNhanVien, LocalDate ngayCapNhat) throws Exception {
        Optional<ChiTietThayDoiGiaPhong> ChiTietThayDoiGiaPhongOptional = repository.findById(new IdChiTietThayDoiGiaPhongEmb(idHangPhong, idNhanVien, ngayCapNhat));
        if(ChiTietThayDoiGiaPhongOptional.isEmpty()){
            throw new Exception("ChiTietThayDoiGiaPhong not found");
        }
        return convertChiTietThayDoiGiaPhongResponse(ChiTietThayDoiGiaPhongOptional.get());
    }

    @PreAuthorize("hasRole('STAFF')")
    @Override
    public List<ChiTietGiaPhongResponse> getAllChiTietThayDoiGiaPhongResponse() {
        List<ChiTietThayDoiGiaPhong> chiTietThayDoiGiaPhongs = repository.findAll();
        Collections.sort(chiTietThayDoiGiaPhongs, new Comparator<ChiTietThayDoiGiaPhong>() {
            @Override
            public int compare(ChiTietThayDoiGiaPhong o1, ChiTietThayDoiGiaPhong o2) {
                return o1.getNgayApDung().isBefore(o2.getNgayApDung()) ? 1 : -1;
            }
        });

        return chiTietThayDoiGiaPhongs.stream().map(chiTietThayDoiGiaPhong -> {
            try {
                return convertChiTietThayDoiGiaPhongResponse(chiTietThayDoiGiaPhong);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();


    }
    
    public ChiTietGiaPhongResponse convertChiTietThayDoiGiaPhongResponse(ChiTietThayDoiGiaPhong chiTietThayDoiGiaPhong) throws Exception {
        ThongTinHangPhong thongTinHangPhong = thongTinHangPhongService
                .getThongTinHangPhongById(chiTietThayDoiGiaPhong.getHangPhong().getIdHangPhong());

        boolean dangApDung = thongTinHangPhong.getGiaGoc() == chiTietThayDoiGiaPhong.getGiaCapNhat();

        return ChiTietGiaPhongResponse.builder()
                .idHangPhong(chiTietThayDoiGiaPhong.getHangPhong().getIdHangPhong())
                .tenHangPhong(chiTietThayDoiGiaPhong.getHangPhong().getTenHangPhong())
                .idNhanVien(chiTietThayDoiGiaPhong.getNhanVien().getIdNhanVien())
                .tenNhanVien(chiTietThayDoiGiaPhong.getNhanVien().getHoTen())
                .giaCapNhat(chiTietThayDoiGiaPhong.getGiaCapNhat())
                .ngayApDung(chiTietThayDoiGiaPhong.getNgayApDung())
                .ngayCapNhat(chiTietThayDoiGiaPhong.getIdChiTietThayDoiGiaPhongEmb().getNgayCapNhat())
                .dangApDung(dangApDung)
                .build();
    }
}
