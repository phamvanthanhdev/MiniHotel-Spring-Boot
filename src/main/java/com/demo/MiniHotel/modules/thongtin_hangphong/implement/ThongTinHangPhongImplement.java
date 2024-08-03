package com.demo.MiniHotel.modules.thongtin_hangphong.implement;

import com.demo.MiniHotel.model.ThongTinHangPhong;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhong2;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongResponse;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongUserResponse;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import com.demo.MiniHotel.repository.ThongTinHangPhongRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThongTinHangPhongImplement implements IThongTinHangPhongService {
    private final ThongTinHangPhongRepository repository;

    @Override
    public List<ThongTinHangPhong> getAllThongTinHangPhong() {
        return repository.findAll();
    }

    @Override
    public ThongTinHangPhong getThongTinHangPhongById(Integer id) throws Exception {
        Optional<ThongTinHangPhong> thongTinHangPhongOptional = repository.findById(id);
        if(thongTinHangPhongOptional.isEmpty())
            throw new Exception("ThongTinHangPhong not found!");
        return thongTinHangPhongOptional.get();
    }

    @Override
    public ThongTinHangPhongUserResponse getThongTinHangPhongSoLuongById(Integer id, LocalDate ngayDenDat, LocalDate ngayDiDat) throws Exception {
        Optional<ThongTinHangPhong> thongTinHangPhongOptional = repository.findById(id);
        if(thongTinHangPhongOptional.isEmpty())
            throw new Exception("ThongTinHangPhong not found!");
        ThongTinHangPhong thongTinHangPhong =  thongTinHangPhongOptional.get();
        Integer soLuongTrong = laySoLuongHangPhongTrong(ngayDenDat, ngayDiDat, id);
        return convertThongTinHangPhongUserResponse(thongTinHangPhong, soLuongTrong);
    }

    @Override
    public Long getDonGiaThongTinHangPhongById(Integer id) throws Exception {
        Optional<ThongTinHangPhong> thongTinHangPhongOptional = repository.findById(id);
        if(thongTinHangPhongOptional.isEmpty())
            throw new Exception("ThongTinHangPhong not found!");
        ThongTinHangPhong thongTinHangPhong = thongTinHangPhongOptional.get();
        if(thongTinHangPhong.getGiaKhuyenMai() != null && thongTinHangPhong.getGiaKhuyenMai() > 0)
            return thongTinHangPhong.getGiaKhuyenMai();
        return thongTinHangPhong.getGiaGoc();
    }

    @Override
    public ThongTinHangPhongResponse convertThongTinHangPhongResponse(ThongTinHangPhong thongTinHangPhong) throws Exception {
        byte[] photoBytes = null;
        Blob photoBlob = thongTinHangPhong.getHinhAnh();
        if(photoBlob!=null){
            try {
                photoBytes = photoBlob.getBytes(1, (int)photoBlob.length());
            }catch (SQLException e){
                throw new Exception("Error retrieving photo");
            }
        }
        String base64Photo = Base64.encodeBase64String(photoBytes);
        return new ThongTinHangPhongResponse(
                thongTinHangPhong.getIdHangPhong(),
                thongTinHangPhong.getTenHangPhong(),
                thongTinHangPhong.getMoTa(),
                base64Photo,
                thongTinHangPhong.getTenKieuPhong(),
                thongTinHangPhong.getTenLoaiPhong(),
                thongTinHangPhong.getPhanTramGiam(),
                thongTinHangPhong.getSoNguoiToiDa(),
                thongTinHangPhong.getGiaGoc(),
                thongTinHangPhong.getGiaKhuyenMai()
        );
    }

    //Kiem tra hạng phòng có trống
    @Autowired
    private EntityManager entityManager;

    @Override
    public Boolean kiemTraPhongHangPhongTrong(int idHangPhong, LocalDate ngayDenDat, LocalDate ngayDiDat, int soLuongDat) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("KiemTraHangPhongTrong")
                .registerStoredProcedureParameter("ngay_den_dat", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ngay_di_dat", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idhang_phong", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("so_luong_dat", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ket_qua", Boolean.class, ParameterMode.OUT)
                .setParameter("ngay_den_dat", ngayDenDat)
                .setParameter("ngay_di_dat", ngayDiDat)
                .setParameter("idhang_phong", idHangPhong)
                .setParameter("so_luong_dat", soLuongDat);

        query.execute();

        return (Boolean) query.getOutputParameterValue("ket_qua");
    }


    @Override
    public Integer laySoLuongHangPhongTrong(LocalDate ngayDenDat, LocalDate ngayDiDat, int idHangPhong) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SoLuongHangPhongTrong")
                .registerStoredProcedureParameter("ngay_den_dat", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ngay_di_dat", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idhang_phong", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("so_luong_phong_trong", Integer.class, ParameterMode.OUT)
                .setParameter("ngay_den_dat", ngayDenDat)
                .setParameter("ngay_di_dat", ngayDiDat)
                .setParameter("idhang_phong", idHangPhong);

        query.execute();

        return (Integer) query.getOutputParameterValue("so_luong_phong_trong");
    }

    // Lấy thông tin hạng phòng có số lượng trống
    @Override
    public List<ThongTinHangPhongUserResponse> getThongTinHangPhongTheoThoiGian(LocalDate ngayDenDat, LocalDate ngayDiDat) throws Exception {
        List<ThongTinHangPhong> thongTinHangPhongs = repository.findAll();
        List<ThongTinHangPhongUserResponse> response2s = new ArrayList<>();
        for (ThongTinHangPhong thongTinHangPhong: thongTinHangPhongs) {
            int soLuongTrong = laySoLuongHangPhongTrong(ngayDenDat, ngayDiDat, thongTinHangPhong.getIdHangPhong());
            response2s.add(convertThongTinHangPhongUserResponse(thongTinHangPhong, soLuongTrong));
        }
        return response2s;
    }

    @Override
    public List<ThongTinHangPhongUserResponse> timKiemThongTinHangPhong(LocalDate ngayDenDat, LocalDate ngayDiDat, Integer soNguoi, Long giaMin, Long giaMax) throws Exception {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("TimKiemHangPhong", ThongTinHangPhong.class)
                .registerStoredProcedureParameter("so_nguoi", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("gia_min", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("gia_max", Long.class, ParameterMode.IN)
                .setParameter("so_nguoi", soNguoi)
                .setParameter("gia_min", giaMin)
                .setParameter("gia_max", giaMax);
        List<ThongTinHangPhong> thongTinHangPhongs = query.getResultList();
        List<ThongTinHangPhongUserResponse> responses = new ArrayList<>();
        for (ThongTinHangPhong thongTinHangPhong: thongTinHangPhongs) {
            int soLuongTrong = laySoLuongHangPhongTrong(ngayDenDat, ngayDiDat, thongTinHangPhong.getIdHangPhong());
            responses.add(convertThongTinHangPhongUserResponse(thongTinHangPhong, soLuongTrong));

        }
        return responses;
    }

    @Override
    public Boolean kiemTraPhongDaThue(String maPhongThue, LocalDate ngayDenThue, LocalDate ngayDiThue) {
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
    public List<ThongTinHangPhongUserResponse> sapXepHangPhongTheoSoLuongDatThue(LocalDate ngayDenDat, LocalDate ngayDiDat) throws Exception {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("HangPhongDatThue",
                        ThongTinHangPhong.class);
        List<ThongTinHangPhong> thongTinHangPhongs = query.getResultList();
        List<ThongTinHangPhongUserResponse> responses = new ArrayList<>();
        for (ThongTinHangPhong thongTinHangPhong: thongTinHangPhongs) {
            int soLuongTrong = laySoLuongHangPhongTrong(ngayDenDat, ngayDiDat, thongTinHangPhong.getIdHangPhong());
            responses.add(convertThongTinHangPhongUserResponse(thongTinHangPhong, soLuongTrong));
        }
        return responses;
    }

    public ThongTinHangPhongUserResponse convertThongTinHangPhongUserResponse(ThongTinHangPhong thongTinHangPhong, int soLuongTrong) throws Exception {
        byte[] photoBytes = null;
        Blob photoBlob = thongTinHangPhong.getHinhAnh();
        if(photoBlob!=null){
            try {
                photoBytes = photoBlob.getBytes(1, (int)photoBlob.length());
            }catch (SQLException e){
                throw new Exception("Error retrieving photo");
            }
        }
        String base64Photo = Base64.encodeBase64String(photoBytes);
        return new ThongTinHangPhongUserResponse(
                thongTinHangPhong.getIdHangPhong(),
                thongTinHangPhong.getTenHangPhong(),
                thongTinHangPhong.getMoTa(),
                base64Photo,
                thongTinHangPhong.getTenKieuPhong(),
                thongTinHangPhong.getTenLoaiPhong(),
                thongTinHangPhong.getPhanTramGiam(),
                thongTinHangPhong.getSoNguoiToiDa(),
                thongTinHangPhong.getGiaGoc(),
                thongTinHangPhong.getGiaKhuyenMai(),
                soLuongTrong
        );
    }

    public ThongTinHangPhongUserResponse convertThongTinHangPhong2Response(ThongTinHangPhong2 thongTinHangPhong2) throws Exception {
        byte[] photoBytes = null;
        Blob photoBlob = thongTinHangPhong2.getHinhAnh();
        if(photoBlob!=null){
            try {
                photoBytes = photoBlob.getBytes(1, (int)photoBlob.length());
            }catch (SQLException e){
                throw new Exception("Error retrieving photo");
            }
        }
        String base64Photo = Base64.encodeBase64String(photoBytes);
        return new ThongTinHangPhongUserResponse(
                thongTinHangPhong2.getIdHangPhong(),
                thongTinHangPhong2.getTenHangPhong(),
                thongTinHangPhong2.getMoTa(),
                base64Photo,
                thongTinHangPhong2.getTenKieuPhong(),
                thongTinHangPhong2.getTenLoaiPhong(),
                thongTinHangPhong2.getPhanTramGiam(),
                thongTinHangPhong2.getSoNguoiToiDa(),
                thongTinHangPhong2.getGiaGoc(),
                thongTinHangPhong2.getGiaKhuyenMai(),
                thongTinHangPhong2.getSoLuongTrong()
        );
    }
}
