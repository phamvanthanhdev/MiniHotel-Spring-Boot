package com.demo.MiniHotel.modules.chitiet_ctkm.implement;

import com.demo.MiniHotel.embedded.IdChiTietKhuyenMaiEmb;
import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_ctkm.dto.ChiTietKhuyenMaiRequest;
import com.demo.MiniHotel.modules.chitiet_ctkm.dto.ChiTietKhuyenMaiResponse;
import com.demo.MiniHotel.modules.chitiet_ctkm.service.IChiTietKhuyenMaiService;
import com.demo.MiniHotel.modules.ctkm.service.IChuongTrinhKhuyenMaiService;
import com.demo.MiniHotel.modules.hangphong.service.IHangPhongService;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import com.demo.MiniHotel.repository.ChiTietKhuyenMaiRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChiTietKhuyenMaiImplement implements IChiTietKhuyenMaiService {
    ChiTietKhuyenMaiRepository repository;

    IHangPhongService hangPhongService;
    IChuongTrinhKhuyenMaiService chuongTrinhKhuyenMaiService;

    IThongTinHangPhongService thongTinHangPhongService;

    @Autowired
    private EntityManager entityManager;

    @PreAuthorize("hasRole('STAFF')")
    @Override
    public ChiTietKhuyenMai themChiTietKhuyenMai(ChiTietKhuyenMaiRequest request) throws Exception {
        ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = chuongTrinhKhuyenMaiService
                .getChuongTrinhKhuyenMaiById(request.getIdKhuyenMai());

        // Kiểm tra CTKM còn đang hoạt động không
        LocalDate ngayHienTai = LocalDate.now();
        if(ngayHienTai.isAfter(chuongTrinhKhuyenMai.getNgayKetThuc()))
            throw new AppException(ErrorCode.CTKM_NOT_AVAILABLE);

        //Kiểm tra chi tiết khuyến mãi có trùng không
        IdChiTietKhuyenMaiEmb idChiTietKhuyenMaiEmb = new IdChiTietKhuyenMaiEmb(
                request.getIdKhuyenMai(), request.getIdHangPhong());
        if(repository.existsById(idChiTietKhuyenMaiEmb))
            throw new AppException(ErrorCode.CHITIETKM_EXISTED);

        //Kiểm tra hạng phòng có đang được khuyến mãi không
//        ThongTinHangPhong thongTinHangPhong = thongTinHangPhongService.getThongTinHangPhongById(request.getIdHangPhong());
//        if(thongTinHangPhong.getPhanTramGiam() > 0)
//            throw new AppException(ErrorCode.HANGPHONG_KM_EXISTED);

        // Kiểm tra hạng phòng có đang nằm trong CTKM trùng thời gian không

        HangPhong hangPhong = hangPhongService.getHangPhongById(request.getIdHangPhong());
        List<HangPhong> hangPhongs = layDanhSachHangPhongKhuyenMai(
                chuongTrinhKhuyenMai.getNgayBatDau(),
                chuongTrinhKhuyenMai.getNgayKetThuc());
        if(hangPhongs.contains(hangPhong)) {
            // Kiểm tra CTKM bị trùng có kết thúc hay chưa, nếu kết thúc rồi thì vẫn cho thêm
            ThongTinHangPhong thongTinHangPhong = thongTinHangPhongService.getThongTinHangPhongById(request.getIdHangPhong());
            if(thongTinHangPhong.getPhanTramGiam() > 0) {
                throw new AppException(ErrorCode.HANGPHONG_KM_EXISTED);
            }
        }

//        HangPhong hangPhong = hangPhongService.getHangPhongById(request.getIdHangPhong());

        ChiTietKhuyenMai chiTietKhuyenMai = new ChiTietKhuyenMai();

        chiTietKhuyenMai.setIdChiTietKhuyenMaiEmb(idChiTietKhuyenMaiEmb);
        chiTietKhuyenMai.setChuongTrinhKhuyenMai(chuongTrinhKhuyenMai);
        chiTietKhuyenMai.setHangPhong(hangPhong);
        chiTietKhuyenMai.setPhanTramGiam(request.getPhanTramGiam());

        return repository.save(chiTietKhuyenMai);
    }

    @PreAuthorize("hasRole('STAFF')")
    @Override
    public List<ChiTietKhuyenMai> getAllChiTietKhuyenMai() {
        return repository.findAll();
    }

    @PreAuthorize("hasRole('STAFF')")
    @Override
    public ChiTietKhuyenMai getChiTietKhuyenMaiById(int idKhuyenMai, int idHangPhong) throws Exception {
        Optional<ChiTietKhuyenMai> ChiTietKhuyenMaiOptional = repository.findById(new IdChiTietKhuyenMaiEmb(idKhuyenMai, idHangPhong));
        if(ChiTietKhuyenMaiOptional.isEmpty()){
            throw new Exception("ChiTietKhuyenMai not found");
        }
        return ChiTietKhuyenMaiOptional.get();
    }
    @PreAuthorize("hasRole('STAFF')")
    @Override
    public ChiTietKhuyenMai updateChiTietKhuyenMai(ChiTietKhuyenMaiRequest request, int idKhuyenMai, int idHangPhong) throws Exception {
        ChiTietKhuyenMai chiTietKhuyenMai = getChiTietKhuyenMaiById(idKhuyenMai, idHangPhong);

        chiTietKhuyenMai.setChuongTrinhKhuyenMai(chuongTrinhKhuyenMaiService
                .getChuongTrinhKhuyenMaiById(request.getIdKhuyenMai()));
        chiTietKhuyenMai.setHangPhong(hangPhongService
                .getHangPhongById(request.getIdHangPhong()));
        chiTietKhuyenMai.setPhanTramGiam(request.getPhanTramGiam());

        return repository.save(chiTietKhuyenMai);
    }
    @PreAuthorize("hasRole('STAFF')")
    @Override
    public void deleteChiTietKhuyenMai(int idKhuyenMai, int idHangPhong) throws Exception {
        Optional<ChiTietKhuyenMai> ChiTietKhuyenMaiOptional = repository.findById(new IdChiTietKhuyenMaiEmb(idKhuyenMai, idHangPhong));
        if(ChiTietKhuyenMaiOptional.isEmpty()){
            throw new Exception("ChiTietKhuyenMai not found");
        }
        repository.deleteById(new IdChiTietKhuyenMaiEmb(idKhuyenMai, idHangPhong));
    }
    @PreAuthorize("hasRole('STAFF')")
    @Override
    public ChiTietKhuyenMaiResponse getChiTietKhuyenMaiResponseById(int idKhuyenMai, int idHangPhong) throws Exception {
        Optional<ChiTietKhuyenMai> chiTietKhuyenMaiOptional = repository.findById(new IdChiTietKhuyenMaiEmb(idKhuyenMai, idHangPhong));
        if(chiTietKhuyenMaiOptional.isEmpty()){
            throw new Exception("ChiTietKhuyenMai not found");
        }
        return convertChiTietKhuyenMaiResponse(chiTietKhuyenMaiOptional.get());
    }

    @PreAuthorize("hasRole('STAFF')")
    @Override
    public List<ChiTietKhuyenMaiResponse> getAllChiTietKhuyenMaiResponse() {
        return repository.findAll().stream().map(chiTietKhuyenMai -> {
            try {
                return convertChiTietKhuyenMaiResponse(chiTietKhuyenMai);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    // Lấy ra danh sách hạng phòng đang khuyến mãi trong khoảng thời gian


    @Override
    public List<HangPhong> layDanhSachHangPhongKhuyenMai(LocalDate ngayBatDauKiemTra, LocalDate ngayKetThucKiemTra) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("DanhSachHangPhongKhuyenMaiTheoThoiGian", HangPhong.class)
                .registerStoredProcedureParameter("ngay_bat_dau_kiem_tra", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ngay_ket_thuc_kiem_tra", LocalDate.class, ParameterMode.IN)
                .setParameter("ngay_bat_dau_kiem_tra", ngayBatDauKiemTra)
                .setParameter("ngay_ket_thuc_kiem_tra", ngayKetThucKiemTra);
        return query.getResultList();
    }

    @Override
    public List<ChiTietKhuyenMaiResponse> getChiTietKhuyenMaiByIdKhuyenMai(int idKhuyenMai) throws Exception {
        ChuongTrinhKhuyenMai chuongTrinhKhuyenMai =
                chuongTrinhKhuyenMaiService.getChuongTrinhKhuyenMaiById(idKhuyenMai);
        return chuongTrinhKhuyenMai.getChiTietKhuyenMais().stream().map(chiTietKhuyenMai -> {
            try {
                return convertChiTietKhuyenMaiResponse(chiTietKhuyenMai);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    public ChiTietKhuyenMaiResponse convertChiTietKhuyenMaiResponse(ChiTietKhuyenMai chiTietKhuyenMai) throws Exception {
        ThongTinHangPhong thongTinHangPhong = thongTinHangPhongService
                .getThongTinHangPhongById(chiTietKhuyenMai.getHangPhong().getIdHangPhong());

        long tienGiam = thongTinHangPhong.getGiaGoc() * (chiTietKhuyenMai.getPhanTramGiam())/100;
        long giaKhuyenMai = thongTinHangPhong.getGiaGoc() - tienGiam;

        String trangThai = "";
        ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = chiTietKhuyenMai.getChuongTrinhKhuyenMai();
        LocalDate ngayHienTai = LocalDate.now();

        if(chuongTrinhKhuyenMai.getNgayKetThuc().isBefore(ngayHienTai)){
            trangThai = "Quá hạn áp dụng";
        }else{
            if(!chuongTrinhKhuyenMai.getNgayBatDau().isAfter(ngayHienTai))
                trangThai = "Đang áp dụng";
            else
                trangThai = "Chưa áp dụng";
        }


//        if((chuongTrinhKhuyenMai.getNgayBatDau().isBefore(ngayHienTai)
//                && chuongTrinhKhuyenMai.getNgayKetThuc().isAfter(ngayHienTai))
//                || chuongTrinhKhuyenMai.getNgayBatDau().equals(ngayHienTai)
//                || chuongTrinhKhuyenMai.getNgayKetThuc().equals(ngayHienTai)
//        ){
//            trangThai = true;
//        }

        return ChiTietKhuyenMaiResponse.builder()
                .idKhuyenMai(chiTietKhuyenMai.getIdChiTietKhuyenMaiEmb().getIdKhuyenMai())
                .moTaKhuyenMai(chiTietKhuyenMai.getChuongTrinhKhuyenMai().getMoTa())
                .idHangPhong(chiTietKhuyenMai.getIdChiTietKhuyenMaiEmb().getIdHangPhong())
                .tenHangPhong(chiTietKhuyenMai.getHangPhong().getTenHangPhong())
                .phanTramGiam(chiTietKhuyenMai.getPhanTramGiam())
                .tienGiam(tienGiam)
                .giaGoc(thongTinHangPhong.getGiaGoc())
                .giaKhuyenMai(giaKhuyenMai)
                .trangThai(trangThai)
                .build();
    }
}
