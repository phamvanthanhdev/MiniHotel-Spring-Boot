package com.demo.MiniHotel.modules.thongtin_phong.implement;

import com.demo.MiniHotel.model.Phong;
import com.demo.MiniHotel.model.ThongTinPhong;
import com.demo.MiniHotel.model.ThongTinPhongHienTai;
import com.demo.MiniHotel.modules.thongtin_phong.dto.PhongTrongResponse;
import com.demo.MiniHotel.modules.thongtin_phong.dto.ThongTinPhongHienTaiResponse;
import com.demo.MiniHotel.modules.thongtin_phong.dto.ThongTinPhongResponse;
import com.demo.MiniHotel.modules.thongtin_phong.service.IThongTinPhongService;
import com.demo.MiniHotel.repository.ThongTinPhongHienTaiRepository;
import com.demo.MiniHotel.repository.ThongTinPhongRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThongTinPhongImplement implements IThongTinPhongService {
    private final ThongTinPhongRepository repository;
    private final ThongTinPhongHienTaiRepository phongHienTaiRepository;
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<ThongTinPhongResponse> getThongTinPhongTheoThoiGian(LocalDate ngayDenThue, LocalDate ngayDiThue) {
        List<ThongTinPhong> thongTinPhongs = getAllThongTinPhong();
        List<ThongTinPhongResponse> responses = new ArrayList<>();
        for (ThongTinPhong phong: thongTinPhongs) {
            boolean ketQuaThue = kiemTraPhongThue(ngayDenThue, ngayDiThue, phong.getMaPhong());
            responses.add(convertThongTinPhongResponse(phong, ketQuaThue));
        }
        return responses;
    }

    @Override
    public List<ThongTinPhong> getAllThongTinPhong() {
        return repository.findAll();
    }

    @Override
    public Boolean kiemTraPhongThue(LocalDate ngayDenThue, LocalDate ngayDiThue, String maPhongThue) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("KiemTraPhongThue")
                .registerStoredProcedureParameter("ngay_den_thue", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ngay_di_thue", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ma_phong_thue", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ket_qua_thue", Boolean.class, ParameterMode.OUT)
                .setParameter("ngay_den_thue", ngayDenThue)
                .setParameter("ngay_di_thue", ngayDiThue)
                .setParameter("ma_phong_thue", maPhongThue);
        query.execute();

        return (Boolean) query.getOutputParameterValue("ket_qua_thue");
    }

    @Override
    public List<ThongTinPhongHienTaiResponse> getThongTinPhongHienTai() {
        List<ThongTinPhongHienTai> phongHienTais = phongHienTaiRepository.findAll();
        List<ThongTinPhongHienTaiResponse> responses = new ArrayList<>();
        for (ThongTinPhongHienTai phongHienTai: phongHienTais
             ) {
            responses.add(convertThongTinPhongHienTaiResponse(phongHienTai));
        }
        return responses;
    }

    @Override
    public List<PhongTrongResponse> getPhongTrongByIdHangPhong(int idHangPhong, LocalDate ngayDenThue, LocalDate ngayDiThue) {
        List<ThongTinPhong> thongTinPhongs = repository.findByIdHangPhong(idHangPhong);
        List<PhongTrongResponse> responses = new ArrayList<>();
        for (ThongTinPhong phong: thongTinPhongs) {
            boolean ketQuaThue = kiemTraPhongThue(ngayDenThue, ngayDiThue, phong.getMaPhong());
            if(!ketQuaThue)
                responses.add(convertPhongTrongResponse(phong));
        }
        return responses;
    }

    private ThongTinPhongResponse convertThongTinPhongResponse(ThongTinPhong phong, boolean ketQuaThue) {
        return new ThongTinPhongResponse(phong.getMaPhong(),
                phong.getTang(), phong.getIdHangPhong(), phong.getTenHangPhong(),
                phong.getSoNguoiToiDa(), phong.getGiaGoc(), phong.getGiaKhuyenMai(),
                phong.getTenTrangThai(), ketQuaThue);
    }

    private PhongTrongResponse convertPhongTrongResponse(ThongTinPhong phong) {
        long giaPhong = 0;
        if(phong.getGiaKhuyenMai() > 0) giaPhong = phong.getGiaKhuyenMai();
        else giaPhong = phong.getGiaGoc();
        return new PhongTrongResponse(phong.getMaPhong(), phong.getIdHangPhong(), phong.getTang(),
                phong.getTenTrangThai(), giaPhong);
    }

    private ThongTinPhongHienTaiResponse convertThongTinPhongHienTaiResponse(ThongTinPhongHienTai phongHienTai) {
        return new ThongTinPhongHienTaiResponse(phongHienTai.getMaPhong(),
                phongHienTai.getTang(), phongHienTai.getIdHangPhong(), phongHienTai.getTenHangPhong(),
                phongHienTai.getSoNguoiToiDa(), phongHienTai.getGiaGoc(), phongHienTai.getGiaKhuyenMai(),
                phongHienTai.getTenTrangThai(), phongHienTai.getDaThue(),phongHienTai.getIdchitiet_phieuthue(),
                phongHienTai.getNgayDen(), phongHienTai.getNgayDi());
    }
}
