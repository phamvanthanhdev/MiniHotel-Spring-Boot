package com.demo.MiniHotel.modules.phong.implement;

import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.hangphong.service.IHangPhongService;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.phong.dto.PhongRequest;
import com.demo.MiniHotel.modules.phong.dto.PhongResponse;
import com.demo.MiniHotel.modules.phong.dto.ThemPhongResponse;
import com.demo.MiniHotel.modules.phong.service.IPhongService;
import com.demo.MiniHotel.modules.thongtin_phong.dto.ThongTinPhongHienTaiResponse;
import com.demo.MiniHotel.modules.thongtin_phong.service.IThongTinPhongService;
import com.demo.MiniHotel.modules.trangthai.service.ITrangThaiService;
import com.demo.MiniHotel.repository.PhongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PhongImplement implements IPhongService {
    private final PhongRepository repository;
    private final IHangPhongService hangPhongService;
    private final ITrangThaiService trangThaiService;
    private final IThongTinPhongService thongTinPhongService;
    @Override
    public Phong addNewPhong(PhongRequest request) throws Exception {
        Optional<Phong> phongOptional = repository.findById(request.getMaPhong());
        if(phongOptional.isEmpty()) {
            Phong phong = new Phong();
            phong.setMaPhong(request.getMaPhong());
            phong.setTang(request.getTang());
            phong.setMoTa(request.getMoTa());
            phong.setNgayTao(LocalDate.now());
            phong.setNgayCapNhat(LocalDate.now());

            HangPhong hangPhong = hangPhongService.getHangPhongById(request.getIdHangPhong());
            TrangThai trangThai = trangThaiService.getTrangThaiById(request.getIdTrangThai());

            phong.setHangPhong(hangPhong);
            phong.setTrangThai(trangThai);

            return repository.save(phong);
        }

        throw new AppException(ErrorCode.MAPHONG_EXISTED);
    }

    @Override
    public List<Phong> getAllPhong() {
        return repository.findAll();
    }

    @Override
    public Phong getPhongById(String maPhong) throws Exception {
        Optional<Phong> PhongOptional = repository.findById(maPhong);
        if(PhongOptional.isEmpty()){
            throw new Exception("Phong not found.");
        }

        return PhongOptional.get();
    }

    @Override
    public ThemPhongResponse updatePhong(PhongRequest request, String maPhong) throws Exception {
        Optional<Phong> phongOptional = repository.findById(maPhong);
        if(phongOptional.isPresent()) {
            HangPhong hangPhong = hangPhongService.getHangPhongById(request.getIdHangPhong());
            TrangThai trangThai = trangThaiService.getTrangThaiById(request.getIdTrangThai());

            Phong phong = phongOptional.get();

            phong.setTang(request.getTang());
            phong.setMoTa(request.getMoTa());
            phong.setNgayCapNhat(LocalDate.now());

            phong.setHangPhong(hangPhong);
            phong.setTrangThai(trangThai);

            return convertThemPhongResponse(repository.save(phong));
        }

        throw new AppException(ErrorCode.PHONG_NOT_FOUND);
    }

    @Override
    public void deletePhong(String maPhong) throws Exception {
        Optional<Phong> PhongOptional = repository.findById(maPhong);
        if(PhongOptional.isEmpty()){
            throw new AppException(ErrorCode.PHONG_NOT_FOUND);
        }

        repository.deleteById(maPhong);
    }

    @Override
    public Phong capNhatTrangThai(String maPhong, int idTrangThai) throws Exception {
        TrangThai trangThai = trangThaiService.getTrangThaiById(idTrangThai);
        Phong phong = getPhongById(maPhong);
        phong.setTrangThai(trangThai);
        return repository.save(phong);
    }

    @Override
    public List<PhongResponse> getPhongResponses() throws Exception {
        List<ThongTinPhongHienTaiResponse> thongTinPhongs = thongTinPhongService.getThongTinPhongHienTai();
        Collections.sort(thongTinPhongs, new Comparator<ThongTinPhongHienTaiResponse>() {
            @Override
            public int compare(ThongTinPhongHienTaiResponse o1, ThongTinPhongHienTaiResponse o2) {
                return o1.getTang() > o2.getTang() ? 1 : -1;
            }
        });

        List<PhongResponse> phongResponses = new ArrayList<>();
        for (ThongTinPhongHienTaiResponse thongTinPhong: thongTinPhongs) {
            Phong phong = getPhongById(thongTinPhong.getMaPhong());
            phongResponses.add(convertThongTinPhongToPhongResponse(thongTinPhong, phong));
        }

        return phongResponses;
    }

    @Override
    public ThemPhongResponse getPhongResponseById(String maPhong) throws Exception {
        return convertThemPhongResponse(getPhongById(maPhong));
    }

    public PhongResponse convertThongTinPhongToPhongResponse(ThongTinPhongHienTaiResponse thongTinPhong, Phong phong){
        return PhongResponse.builder()
                .maPhong(thongTinPhong.getMaPhong())
                .tang(thongTinPhong.getTang())
                .idHangPhong(thongTinPhong.getIdHangPhong())
                .tenHangPhong(thongTinPhong.getTenHangPhong())
                .soNguoiToiDa(thongTinPhong.getSoNguoiToiDa())
                .giaGoc(thongTinPhong.getGiaGoc())
                .giaKhuyenMai(thongTinPhong.getGiaKhuyenMai())
                .tenTrangThai(thongTinPhong.getTenTrangThai())
                .daThue(thongTinPhong.getDaThue())
                .ngayTao(phong.getNgayTao())
                .ngayCapNhat(phong.getNgayCapNhat())
                .build();
    }

    public ThemPhongResponse convertThemPhongResponse(Phong phong){
        return ThemPhongResponse.builder()
                .maPhong(phong.getMaPhong())
                .tang(phong.getTang())
                .moTa(phong.getMoTa())
                .idHangPhong(phong.getHangPhong().getIdHangPhong())
                .idTrangThai(phong.getTrangThai().getIdTrangThai())
                .ngayTao(phong.getNgayTao())
                .ngayCapNhat(phong.getNgayCapNhat())
                .build();
    }
}
