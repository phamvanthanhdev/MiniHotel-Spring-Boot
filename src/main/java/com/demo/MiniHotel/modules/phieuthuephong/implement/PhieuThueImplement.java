package com.demo.MiniHotel.modules.phieuthuephong.implement;

import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueRequest;
import com.demo.MiniHotel.modules.chitiet_phieuthue.service.IChiTietPhieuThueService;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import com.demo.MiniHotel.modules.phieudatphong.exception.SoLuongPhongTrongException;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
import com.demo.MiniHotel.modules.phieuthuephong.dto.*;
import com.demo.MiniHotel.modules.phieuthuephong.service.IPhieuThueService;
import com.demo.MiniHotel.modules.phong.service.IPhongService;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongUserResponse;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import com.demo.MiniHotel.repository.PhieuThuePhongRepository;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PhieuThueImplement implements IPhieuThueService {
    private final PhieuThuePhongRepository repository;
    private final IKhachHangService khachHangService;
    private final INhanVienService nhanVienService;
    private final IChiTietPhieuThueService chiTietPhieuThueService;
    private final IPhieuDatService phieuDatService;
    private final IThongTinHangPhongService thongTinHangPhongService;
    private final IPhongService phongService;
    @Override
    public PhieuThuePhong addNewPhieuThuePhong(PhieuThuePhongRequest request) throws Exception {
        PhieuThuePhong phieuThuePhong = new PhieuThuePhong();
        phieuThuePhong.setNgayDen(request.getNgayDen());
        phieuThuePhong.setNgayDi(request.getNgayDi());
        phieuThuePhong.setNgayTao(request.getNgayTao());
        phieuThuePhong.setNgayTao(request.getNgayTao());

        KhachHang khachHang = khachHangService.getKhachHangById(request.getIdKhachHang());
        phieuThuePhong.setKhachHang(khachHang);

        NhanVien nhanVien = nhanVienService.getNhanVienById(request.getIdNhanVien());
        phieuThuePhong.setNhanVien(nhanVien);

        if(request.getIdPhieuDat() != null){
            PhieuDatPhong phieuDatPhong = phieuDatService.getPhieuDatPhongById(request.getIdPhieuDat());
            phieuThuePhong.setPhieuDatPhong(phieuDatPhong);
        }

        //Trường hợp đã đặt phòng trước
        /*if(request.getIdPhieuDat() != null && request.getIdPhieuDat() > 0){
            PhieuDatPhong phieuDatPhong = phieuDatService.getPhieuDatPhongById(request.getIdPhieuDat());
            phieuThuePhong.setPhieuDatPhong(phieuDatPhong);
        }else{
        //Trường hợp chưa đặt phòng trước (Kiểm tra hạng phòng còn trống phòng nào hay không)
            Map<Integer, Integer> soLuongHangPhong = new HashMap<>();
            for (ChiTietRequest chiTietRequest: request.getChiTietRequests()) {
                if(soLuongHangPhong.containsKey(chiTietRequest.getIdHangPhong())){

                }
            }
            for (ChiTietRequest chiTietRequest: request.getChiTietRequests()) {
                Phong phong = phongService.getPhongById(chiTietRequest.getMaPhong());
//                int soLuongDat = ;
                if (!thongTinHangPhongService.kiemTraPhongHangPhongTrong(
                        phong.getHangPhong().getIdHangPhong(), request.getNgayDen(),
                        request.getNgayDi(), 1)) {
                    throw new SoLuongPhongTrongException("Hạng phòng này không còn phòng trống");
                }
            }
        }*/

        // Kiểm tra phòng này có đang được đặt hay chưa
        /*for (ChiTietRequest chiTietRequest: request.getChiTietRequests()) {
            if (!thongTinHangPhongService.kiemTraPhongDaThue(
                    chiTietRequest.getMaPhong(), request.getNgayDen(),
                    request.getNgayDi())) {
                throw new SoLuongPhongTrongException("Phòng này đang được cho thuê");
            }
        }*/

        PhieuThuePhong newPhieuThue = repository.save(phieuThuePhong);

        //Luu chi tiet phieu thue
        for (ChiTietRequest chiTietRequest: request.getChiTietRequests()) {
            ChiTietPhieuThueRequest chiTietPhieuThueRequest = new ChiTietPhieuThueRequest();
            chiTietPhieuThueRequest.setIdPhieuThue(newPhieuThue.getIdPhieuThue());
            chiTietPhieuThueRequest.setMaPhong(chiTietRequest.getMaPhong());
            chiTietPhieuThueRequest.setNgayDen(chiTietRequest.getNgayDen());
            chiTietPhieuThueRequest.setNgayDi(chiTietRequest.getNgayDi());
            chiTietPhieuThueRequest.setDonGia(chiTietRequest.getDonGia());

            chiTietPhieuThueService.addNewChiTietPhieuThue(chiTietPhieuThueRequest);
        }

        return newPhieuThue;
    }

    @Override
    public List<PhieuThuePhong> getAllPhieuThuePhong() {
        return repository.findAll();
    }

    @Override
    public PhieuThuePhong getPhieuThuePhongById(Integer id) throws Exception {
        Optional<PhieuThuePhong> PhieuThuePhongOptional = repository.findById(id);
        if(PhieuThuePhongOptional.isEmpty()){
            throw new Exception("PhieuThuePhong not found.");
        }

        return PhieuThuePhongOptional.get();
    }

    @Override
    public PhieuThuePhong updatePhieuThuePhong(PhieuThuePhongRequest request, Integer id) throws Exception {
        PhieuThuePhong phieuThuePhong = getPhieuThuePhongById(id);

        phieuThuePhong.setNgayDen(request.getNgayDen());
        phieuThuePhong.setNgayDi(request.getNgayDi());
        phieuThuePhong.setNgayTao(request.getNgayTao());

        KhachHang khachHang = khachHangService.getKhachHangById(request.getIdKhachHang());
        phieuThuePhong.setKhachHang(khachHang);

        NhanVien nhanVien = nhanVienService.getNhanVienById(request.getIdNhanVien());
        phieuThuePhong.setNhanVien(nhanVien);

        if(request.getIdPhieuDat() != null && request.getIdPhieuDat() > 0){
            PhieuDatPhong phieuDatPhong = phieuDatService.getPhieuDatPhongById(request.getIdPhieuDat());
            phieuThuePhong.setPhieuDatPhong(phieuDatPhong);
        }

        return repository.save(phieuThuePhong);
    }

    @Override
    public void deletePhieuThuePhong(Integer id) throws Exception {
        Optional<PhieuThuePhong> PhieuThuePhongOptional = repository.findById(id);
        if(PhieuThuePhongOptional.isEmpty()){
            throw new Exception("PhieuThuePhong not found.");
        }

        repository.deleteById(id);
    }

    /*@Override
    public List<ChiTietPhieuThue> getChiTietPhieuThuesByIdPhieuThue(Integer idPhieuThue) throws Exception {
        return getPhieuThuePhongById(idPhieuThue).getChiTietPhieuThues();
    }*/

    /*@Override
    public ChiTietPhieuThue addKhachHangToChiTietPhieuThue(ChiTietKhachThueRequest chiTietKhachThueRequest) throws Exception {
        List<KhachHang> khachHangs = new ArrayList<>();
        for (Integer idKhachHang: chiTietKhachThueRequest.getIdKhachThues()) {
            khachHangs.add(khachHangService.getKhachHangById(idKhachHang));
        }

        return chiTietPhieuThueService.addKhachHangToChiTietPhieuThue(chiTietKhachThueRequest.getIdChiTietPhieuThue(), khachHangs);
    }*/

    /*@Override
    public ChiTietPhieuThue removeKhachHangInChiTietPhieuThue(DelChiTietKhachThueRequest delChiTietKhachThueRequest) throws Exception {
        KhachHang khachHang = khachHangService.getKhachHangById(delChiTietKhachThueRequest.getIdKhachThue());
        return chiTietPhieuThueService.removeKhachHangInChiTietPhieuThue(delChiTietKhachThueRequest.getIdChiTietPhieuThue(), khachHang);
    }*/

    @Override
    public ChiTietPhieuThue getChiTietPhieuThueById(Integer idChiTietPhieuThue) throws Exception {
        return chiTietPhieuThueService.getChiTietPhieuThueById(idChiTietPhieuThue);
    }

    @Override
    public PhieuThueResponse getPhieuThuePhongResonseById(Integer id) throws Exception {
        PhieuThuePhong phieuThuePhong = getPhieuThuePhongById(id);
        return convertPhieuThuePhongResponse(phieuThuePhong);
    }

    @Autowired
    private EntityManager entityManager;
    @Override
    public List<PhieuThueResponse> timKiemPhieuThueTheoCmnd(String cmnd) throws Exception {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PhieuThueKhachHang", PhieuThuePhong.class)
                .registerStoredProcedureParameter("cmnd_tim_kiem", String.class, ParameterMode.IN)
                .setParameter("cmnd_tim_kiem", cmnd);
        List<PhieuThuePhong> phieuThuePhongs = query.getResultList();
        List<PhieuThueResponse> responses = new ArrayList<>();
        for (PhieuThuePhong phieuThuePhong: phieuThuePhongs) {
            PhieuThueResponse response = convertPhieuThuePhongResponse(phieuThuePhong);
            responses.add(response);
        }
        return responses;
    }

    // Kiểm tra số lượng phòng trả có phải là phòng cuối cùng không
    @Override
    public Boolean kiemTraChiTietPhieuThueCuoiCung(int soLuong, int idPhieuThue) throws Exception {
        List<ChiTietPhieuThue> chiTietPhieuThues = chiTietPhieuThueService.getChiTietPhieuThueByIdPhieuThue(idPhieuThue);
        List<ChiTietPhieuThue> chiTietPhieuThuesChuaThanhToan = new ArrayList<>();
        for (ChiTietPhieuThue chiTietPhieuThue: chiTietPhieuThues) {
            if(!chiTietPhieuThue.getDaThanhToan())
                chiTietPhieuThuesChuaThanhToan.add(chiTietPhieuThue);
        }
        if(soLuong >= chiTietPhieuThuesChuaThanhToan.size()){
            return true;
        }
        return false;
    }


    private PhieuThueResponse convertPhieuThuePhongResponse(PhieuThuePhong phieuThuePhong){
        Integer idPhieuDat = phieuThuePhong.getPhieuDatPhong() == null ? null : phieuThuePhong.getPhieuDatPhong().getIdPhieuDat();
        Long tienTamUng = phieuThuePhong.getPhieuDatPhong() == null ? 0 : phieuThuePhong.getPhieuDatPhong().getTienTamUng();
        return new PhieuThueResponse(
                phieuThuePhong.getIdPhieuThue(),
                phieuThuePhong.getNgayDen(),
                phieuThuePhong.getNgayDi(),
                phieuThuePhong.getNgayTao(),
                phieuThuePhong.getKhachHang().getHoTen(),
                phieuThuePhong.getKhachHang().getCmnd(),
                phieuThuePhong.getNhanVien().getIdNhanVien(),
                phieuThuePhong.getNhanVien().getHoTen(),
                idPhieuDat,
                tienTamUng);
    }
}
