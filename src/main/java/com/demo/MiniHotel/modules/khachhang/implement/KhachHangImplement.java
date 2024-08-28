package com.demo.MiniHotel.modules.khachhang.implement;

import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.model.PhieuDatPhong;
import com.demo.MiniHotel.model.TaiKhoan;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangRequest;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangResponse;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
import com.demo.MiniHotel.modules.taikhoan.service.ITaiKhoanService;
import com.demo.MiniHotel.repository.KhachHangRepository;
import com.demo.MiniHotel.repository.PhieuDatPhongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KhachHangImplement implements IKhachHangService {
    private final KhachHangRepository repository;
    private final ITaiKhoanService taiKhoanService;
    private final PhieuDatPhongRepository phieuDatPhongRepository;
    @Override
    public KhachHangResponse addNewKhachHang(KhachHangRequest request) throws Exception {
        KhachHang khachHang = new KhachHang();
        khachHang.setCmnd(request.getCmnd());
        khachHang.setHoTen(request.getHoTen());
        khachHang.setSdt(request.getSdt());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setMaSoThue(null);
        khachHang.setEmail(request.getEmail());

        if(request.getIdTaiKhoan() != null){
            TaiKhoan taiKhoan = taiKhoanService.getTaiKhoanById(request.getIdTaiKhoan());
            khachHang.setTaiKhoan(taiKhoan);
        }
        KhachHang newKhachHang = repository.save(khachHang);
        KhachHangResponse response = convertKhachHangToResponse(newKhachHang);

        return response;
    }

    @Override
    public List<KhachHang> getAllKhachHang() {
        return repository.findAll();
    }

    @Override
    public KhachHang getKhachHangById(Integer id) throws Exception {
        Optional<KhachHang> KhachHangOptional = repository.findById(id);
        if(KhachHangOptional.isEmpty()){
            throw new Exception("KhachHang not found.");
        }
        return KhachHangOptional.get();
    }

    @Override
    public KhachHangResponse getKhachHangBySdt(String sdt) throws Exception {
        Optional<KhachHang> khachHangOptional = repository.findBySdt(sdt);
        if(khachHangOptional.isEmpty()){
            throw new Exception("KhachHang not found.");
        }
        KhachHang khachHang = khachHangOptional.get();
        return convertKhachHangToResponse(khachHang);
    }

    @Override
    public KhachHangResponse getKhachHangByIdPhieuDat(int idPhieuDat) throws Exception {
        Optional<PhieuDatPhong> phieuDatPhongOptional = phieuDatPhongRepository.findById(idPhieuDat);
        if(phieuDatPhongOptional.isEmpty()){
            throw new Exception("PhieuDat not found.");
        }
        PhieuDatPhong phieuDatPhong = phieuDatPhongOptional.get();
        if(phieuDatPhong.getKhachHang() != null) {
            KhachHang khachHang = phieuDatPhong.getKhachHang();
            return convertKhachHangToResponse(khachHang);
        }
        return null;
    }

    private KhachHangResponse convertKhachHangToResponse(KhachHang khachHang) {
        return new KhachHangResponse(khachHang.getIdKhachHang(), khachHang.getCmnd(),
                khachHang.getHoTen(), khachHang.getSdt(), khachHang.getDiaChi(), khachHang.getEmail());
    }

    @Override
    public KhachHang updateKhachHang(KhachHangRequest request, Integer id) throws Exception {
        KhachHang khachHang = getKhachHangById(id);

        khachHang.setCmnd(request.getCmnd());
        khachHang.setHoTen(request.getHoTen());
        khachHang.setSdt(request.getSdt());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setMaSoThue(null);
        khachHang.setEmail(request.getEmail());

        if(request.getIdTaiKhoan() != null){
            TaiKhoan taiKhoan = taiKhoanService.getTaiKhoanById(request.getIdTaiKhoan());
            khachHang.setTaiKhoan(taiKhoan);
        }

        return repository.save(khachHang);
    }

    @Override
    public void deleteKhachHang(Integer id) throws Exception {
        Optional<KhachHang> KhachHangOptional = repository.findById(id);
        if(KhachHangOptional.isEmpty()){
            throw new Exception("KhachHang not found.");
        }

        repository.deleteById(id);
    }

    @Override
    public KhachHangResponse getKhachHangResponseById(Integer id) throws Exception {
        KhachHang khachHang = getKhachHangById(id);
        return convertKhachHangToResponse(khachHang);
    }

    @Override
    public KhachHangResponse updateKhachHangResponse(int id, KhachHangRequest request) throws Exception {
        KhachHang khachHang = getKhachHangById(id);

        khachHang.setCmnd(request.getCmnd());
        khachHang.setHoTen(request.getHoTen());
        khachHang.setSdt(request.getSdt());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setMaSoThue(null);
        khachHang.setEmail(request.getEmail());

        KhachHang newKhachHang = repository.save(khachHang);
        return convertKhachHangToResponse(newKhachHang);
    }
}
