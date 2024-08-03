package com.demo.MiniHotel.modules.hangphong.implement;

import com.demo.MiniHotel.embedded.IdHangPhongEmb;
import com.demo.MiniHotel.model.HangPhong;
import com.demo.MiniHotel.model.KieuPhong;
import com.demo.MiniHotel.model.LoaiPhong;
import com.demo.MiniHotel.model.ThongTinHangPhong;
import com.demo.MiniHotel.modules.hangphong.dto.HangPhongRequest;
import com.demo.MiniHotel.modules.hangphong.dto.HangPhongResponse;
import com.demo.MiniHotel.modules.hangphong.service.IHangPhongService;
import com.demo.MiniHotel.modules.kieuphong.service.IKieuPhongService;
import com.demo.MiniHotel.modules.loaiphong.service.ILoaiPhongService;
import com.demo.MiniHotel.repository.HangPhongRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HangPhongImplement implements IHangPhongService {
    private final HangPhongRepository repository;
    private final ILoaiPhongService loaiPhongService;
    private final IKieuPhongService kieuPhongService;
    @Override
    public HangPhong addNewHangPhong(Integer idLoaiPhong, Integer idKieuPhong,
                                     String tenHangPhong,String moTa,
                                     MultipartFile file) throws Exception {
        LoaiPhong loaiPhong = loaiPhongService.getLoaiPhongById(idLoaiPhong);
        KieuPhong kieuPhong = kieuPhongService.getKieuPhongById(idKieuPhong);

        HangPhong hangPhong = new HangPhong();

        if(!file.isEmpty()){
            byte[] photoBytes = file.getBytes();
            Blob photoBlod = new SerialBlob(photoBytes);
            hangPhong.setHinhAnh(photoBlod);
        }

        hangPhong.setTenHangPhong(tenHangPhong);
        if(moTa != null) hangPhong.setMoTa(moTa);
        hangPhong.setLoaiPhong(loaiPhong);
        hangPhong.setKieuPhong(kieuPhong);

        return repository.save(hangPhong);
    }

    @Override
    public List<HangPhong> getAllHangPhong() {
        return repository.findAll();
    }

    @Override
    public HangPhong getHangPhongById(Integer id) throws Exception {
        Optional<HangPhong> HangPhongOptional = repository.findByIdHangPhong(id);
        if(HangPhongOptional.isEmpty()){
            throw new Exception("HangPhong not found.");
        }

        return HangPhongOptional.get();
    }

    @Override
    public String getHinhAnhByIdHangPhong(Integer id) throws Exception {
        Optional<HangPhong> HangPhongOptional = repository.findByIdHangPhong(id);
        if(HangPhongOptional.isEmpty()){
            throw new Exception("HangPhong not found.");
        }
        HangPhong hangPhong = HangPhongOptional.get();
        byte[] photoBytes = null;
        Blob photoBlob = hangPhong.getHinhAnh();
        if(photoBlob!=null){
            try {
                photoBytes = photoBlob.getBytes(1, (int)photoBlob.length());
            }catch (SQLException e){
                throw new Exception("Error retrieving photo");
            }
        }
        String base64Photo = Base64.encodeBase64String(photoBytes);

        return base64Photo;
    }

    @Override
    public HangPhong updateHangPhong(Integer idLoaiPhong, Integer idKieuPhong,
                                     String tenHangPhong,String moTa,
                                     MultipartFile file, Integer id) throws Exception {
        Optional<HangPhong> hangPhongOptional = repository.findByIdHangPhong(id);
        if(hangPhongOptional.isPresent()){
            LoaiPhong loaiPhong = loaiPhongService.getLoaiPhongById(idLoaiPhong);
            KieuPhong kieuPhong = kieuPhongService.getKieuPhongById(idKieuPhong);
            HangPhong hangPhong = hangPhongOptional.get();

            if(!file.isEmpty()){
                byte[] photoBytes = file.getBytes();
                Blob photoBlod = new SerialBlob(photoBytes);
                hangPhong.setHinhAnh(photoBlod);
            }

            hangPhong.setTenHangPhong(tenHangPhong);
            if(moTa != null) hangPhong.setMoTa(moTa);
            hangPhong.setLoaiPhong(loaiPhong);
            hangPhong.setKieuPhong(kieuPhong);

            return repository.save(hangPhong);
        }

        throw new Exception("HangPhong not found!");
    }

    @Override
    public void deleteHangPhong(Integer id) throws Exception {
        Optional<HangPhong> HangPhongOptional = repository.findByIdHangPhong(id);
        if(HangPhongOptional.isEmpty()){
            throw new Exception("HangPhong not found.");
        }

        repository.deleteByIdHangPhong(id);
    }

}
