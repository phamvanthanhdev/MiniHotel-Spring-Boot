package com.demo.MiniHotel.modules.chitiet_phieuthue.implement;

import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietKhachThueResponse;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueRequest;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueResponse;
import com.demo.MiniHotel.modules.chitiet_phieuthue.service.IChiTietPhieuThueService;
import com.demo.MiniHotel.modules.chitiet_phuthu.service.IChiTietPhuThuService;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.service.IChiTietSuDungDichVuService;
import com.demo.MiniHotel.modules.hangphong.service.IHangPhongService;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonResponse;
import com.demo.MiniHotel.modules.hoadon.service.IHoaDonService;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.phieuthuephong.dto.ChiTietKhachThueRequest;
import com.demo.MiniHotel.modules.phieuthuephong.dto.DelChiTietKhachThueRequest;
import com.demo.MiniHotel.modules.phong.service.IPhongService;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import com.demo.MiniHotel.modules.thongtin_phong.dto.ThongTinPhongHienTaiResponse;
import com.demo.MiniHotel.modules.thongtin_phong.service.IThongTinPhongService;
import com.demo.MiniHotel.repository.ChiTietPhieuThueRepository;
import com.demo.MiniHotel.repository.HoaDonRepository;
import com.demo.MiniHotel.repository.PhieuThuePhongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChiTietPhieuThueImplement implements IChiTietPhieuThueService {
    private final ChiTietPhieuThueRepository repository;
    private final PhieuThuePhongRepository PhieuThuePhongRepository;
    private final IPhongService phongService;
    private final IKhachHangService khachHangService;
    private final HoaDonRepository hoaDonRepository;
    private final IHoaDonService hoaDonService;
    private final IChiTietPhuThuService phuThuService;
    private final IChiTietSuDungDichVuService chiTietSuDungDichVuService;
    private final IThongTinHangPhongService thongTinHangPhongService;
    private final IThongTinPhongService thongTinPhongService;
    @Override
    public ChiTietPhieuThue addNewChiTietPhieuThue(ChiTietPhieuThueRequest request) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = new ChiTietPhieuThue();

        Phong phong = phongService.getPhongById(request.getMaPhong());
        PhieuThuePhong phieuThuePhong = PhieuThuePhongRepository.findById(request.getIdPhieuThue()).get();

        chiTietPhieuThue.setPhieuThuePhong(phieuThuePhong);
        chiTietPhieuThue.setPhong(phong);

        chiTietPhieuThue.setNgayDen(request.getNgayDen());
        chiTietPhieuThue.setNgayDi(request.getNgayDi());
        chiTietPhieuThue.setDonGia(request.getDonGia());
        chiTietPhieuThue.setDaThanhToan(false);

        return repository.save(chiTietPhieuThue);
    }

    @Override
    public void addListChiTietPhieuThue(List<ChiTietPhieuThueRequest> requests) throws Exception {
        for (ChiTietPhieuThueRequest request: requests) {
            addNewChiTietPhieuThue(request);
        }
    }

    @Override
    public List<ChiTietPhieuThue> getAllChiTietPhieuThue() {
        return repository.findAll();
    }

    @Override
    public ChiTietPhieuThue getChiTietPhieuThueById(Integer id) throws Exception {
        Optional<ChiTietPhieuThue> ChiTietPhieuThueOptional = repository.findById(id);
        if(ChiTietPhieuThueOptional.isEmpty()){
            throw new Exception("ChiTietPhieuThue not found!");
        }
        return ChiTietPhieuThueOptional.get();
    }

    @Override
    public ChiTietPhieuThueResponse getChiTietPhieuThueResponseById(Integer id) throws Exception {
        Optional<ChiTietPhieuThue> ChiTietPhieuThueOptional = repository.findById(id);
        if(ChiTietPhieuThueOptional.isEmpty()){
            throw new Exception("ChiTietPhieuThue not found!");
        }
        ChiTietPhieuThue chiTietPhieuThue =  ChiTietPhieuThueOptional.get();
        return convertChiTietPhieuThueToResponse(chiTietPhieuThue);
    }

    private ChiTietPhieuThueResponse convertChiTietPhieuThueToResponse(ChiTietPhieuThue chiTietPhieuThue) {
        String tenHangPhong = chiTietPhieuThue.getPhong().getHangPhong().getTenHangPhong();
        int idHangPhong = chiTietPhieuThue.getPhong().getHangPhong().getIdHangPhong();
        return new ChiTietPhieuThueResponse(chiTietPhieuThue.getIdChiTietPhieuThue(),
                chiTietPhieuThue.getPhong().getMaPhong(), idHangPhong, tenHangPhong,
                chiTietPhieuThue.getPhieuThuePhong().getIdPhieuThue(),
                chiTietPhieuThue.getNgayDen(), chiTietPhieuThue.getNgayDi(), chiTietPhieuThue.getDonGia(),
                chiTietPhieuThue.getDaThanhToan());
    }

    @Override
    public List<ChiTietPhieuThue> getChiTietPhieuThueByIdPhieuThue(Integer idPhieuThue) throws Exception {
        return repository.findByPhieuThuePhong_IdPhieuThue(idPhieuThue);
    }

    @Override
    public ChiTietPhieuThue updateChiTietPhieuThue(ChiTietPhieuThueRequest request, Integer id) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(id);

        if(request.getMaPhong() != null) {
            Phong phong = phongService.getPhongById(request.getMaPhong());
            chiTietPhieuThue.setPhong(phong);
        }
        if(request.getIdPhieuThue() != null) {
            PhieuThuePhong phieuThuePhong = PhieuThuePhongRepository.findById(request.getIdPhieuThue()).get();
            chiTietPhieuThue.setPhieuThuePhong(phieuThuePhong);
        }
        if(request.getNgayDen() != null)
            chiTietPhieuThue.setNgayDen(request.getNgayDen());
        if(request.getNgayDi() != null)
            chiTietPhieuThue.setNgayDi(request.getNgayDi());
        if(request.getDonGia() != null)
            chiTietPhieuThue.setDonGia(request.getDonGia());
        if(request.getDaThanhToan() != null)
            chiTietPhieuThue.setDaThanhToan(request.getDaThanhToan());

        return repository.save(chiTietPhieuThue);
    }

    @Override
    public void deleteChiTietPhieuThue(Integer id) throws Exception {
        Optional<ChiTietPhieuThue> ChiTietPhieuThueOptional = repository.findById(id);
        if(ChiTietPhieuThueOptional.isEmpty()){
            throw new Exception("ChiTietPhieuThue not found!");
        }
        repository.deleteById(id);
    }

    @Override
    public void deleteChiTietPhieuThueByIdPhieuThue(Integer idPhieuThue) throws Exception {
        List<ChiTietPhieuThue> chiTietPhieuThues = repository.findByPhieuThuePhong_IdPhieuThue(idPhieuThue);
        if(chiTietPhieuThues.isEmpty()){
            throw new Exception("ChiTietPhieuThue not found!");
        }
        for (ChiTietPhieuThue chiTietPhieuThue:chiTietPhieuThues) {
            repository.deleteById(chiTietPhieuThue.getIdChiTietPhieuThue());
        }
    }

    //Thêm danh sách khách hàng vào chi tiết phiếu thuê
    @Override
    public ChiTietPhieuThue addKhachHangToChiTietPhieuThue(ChiTietKhachThueRequest khachThueRequest) throws Exception {
        List<KhachHang> khachHangs = new ArrayList<>();
        for (Integer idKhachHang: khachThueRequest.getIdKhachThues()) {
            khachHangs.add(khachHangService.getKhachHangById(idKhachHang));
        }

        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(khachThueRequest.getIdChiTietPhieuThue());
        List<KhachHang> khachHangList = chiTietPhieuThue.getKhachHangs();
        khachHangList.addAll(khachHangs);
        chiTietPhieuThue.setKhachHangs(khachHangList);
        return repository.save(chiTietPhieuThue);
    }

    @Override
    public ChiTietPhieuThue removeKhachHangInChiTietPhieuThue(DelChiTietKhachThueRequest delChiTietKhachThueRequest) throws Exception {
        KhachHang khachHang = khachHangService.getKhachHangById(delChiTietKhachThueRequest.getIdKhachThue());

        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(delChiTietKhachThueRequest.getIdChiTietPhieuThue());
        List<KhachHang> khachHangList = chiTietPhieuThue.getKhachHangs();
        khachHangList.remove(khachHang);
        chiTietPhieuThue.setKhachHangs(khachHangList);
        return repository.save(chiTietPhieuThue);
    }

    @Override
    public List<ChiTietKhachThueResponse> getKhachThueHienTai() throws Exception {
        List<ThongTinPhongHienTaiResponse> thongTinPhongHienTais = thongTinPhongService.getThongTinPhongHienTai();
        List<ChiTietKhachThueResponse> chiTietKhachThueResponses = new ArrayList<>();
        for (ThongTinPhongHienTaiResponse thongTinPhong:thongTinPhongHienTais) {
            if(thongTinPhong.getIdChiTietPhieuThue() != null) {
                ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(thongTinPhong.getIdChiTietPhieuThue());
                List<KhachHang> khachHangs = chiTietPhieuThue.getKhachHangs();
                for (KhachHang khachHang:khachHangs) {
                    ChiTietKhachThueResponse khachThueResponse = new ChiTietKhachThueResponse(
                            thongTinPhong.getMaPhong(), khachHang.getHoTen(), khachHang.getSdt(),
                            chiTietPhieuThue.getNgayDen(), chiTietPhieuThue.getNgayDi()
                    );
                    chiTietKhachThueResponses.add(khachThueResponse);
                }
            }
        }
        return chiTietKhachThueResponses;
    }

    @Override
    public HoaDon getHoaDonByChiTietPhieuThue(Integer id) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(id);
        return chiTietPhieuThue.getHoaDon();
    }

    @Override
    public ChiTietPhieuThue addHoaDonToChiTietPhieuThue(Integer id, ChiTietPhieuThueRequest request) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(id);

        if(request.getSoHoaDon() != null) {
            Optional<HoaDon> hoaDonOptional = hoaDonRepository.findById(request.getSoHoaDon());
            if(hoaDonOptional.isEmpty()) throw new Exception("HoaDon not found.");
            HoaDon hoaDon = hoaDonOptional.get();
            chiTietPhieuThue.setHoaDon(hoaDon);
        }

        return repository.save(chiTietPhieuThue);
    }

    @Override
    public ChiTietPhieuThueResponse updateNgayDi(Integer id, LocalDate ngayDi) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(id);
        chiTietPhieuThue.setNgayDi(ngayDi);
        return convertChiTietPhieuThueToResponse(repository.save(chiTietPhieuThue));
    }

    @Override
    public HoaDonResponse traPhongKhachSan(Integer idNhanVien, Long tongTien,
                                           LocalDate ngayTao, Integer idChiTietPhieuThue) throws Exception {

        //tao mot hoa don moi
        HoaDonResponse hoaDonResponse = hoaDonService.themHoaDonMoi(idNhanVien, tongTien, ngayTao);
        String soHoaDon = hoaDonResponse.getSoHoaDon();
        //them hoa don vao chi tiet phieu thue
        themHoaDonToChiTietPhieuThue(idChiTietPhieuThue, hoaDonResponse.getSoHoaDon());
        //dat da thanh toan = true
        thanhToanChiTietPhieuThue(idChiTietPhieuThue);
        //them hoa don vao chi tiet phu thu và chuyển da thanh toan = true
        phuThuService.thanhToanChiTietPhuThuCuaChiTietPhieuThue(idChiTietPhieuThue, soHoaDon);
        // them hoa don vao chi tiet su dung dich vu
        chiTietSuDungDichVuService.thanhToanChiTietSuDungDVCuaChiTietPhieuThue(idChiTietPhieuThue, soHoaDon);
        return hoaDonResponse;
    }

    @Override
    public HoaDonResponse traPhongKhachSanKhachDoan(Integer idNhanVien, Long tongTien, LocalDate ngayTao, List<Integer> idChiTietPhieuThues) throws Exception {
        //tao mot hoa don moi
        HoaDonResponse hoaDonResponse = hoaDonService.themHoaDonMoi(idNhanVien, tongTien, ngayTao);
        String soHoaDon = hoaDonResponse.getSoHoaDon();
        for (int idChiTietPhieuThue: idChiTietPhieuThues) {
            //them hoa don vao chi tiet phieu thue
            themHoaDonToChiTietPhieuThue(idChiTietPhieuThue, hoaDonResponse.getSoHoaDon());
            //dat da thanh toan = true
            thanhToanChiTietPhieuThue(idChiTietPhieuThue);
            //them hoa don vao chi tiet phu thu và chuyển da thanh toan = true
            phuThuService.thanhToanChiTietPhuThuCuaChiTietPhieuThue(idChiTietPhieuThue, soHoaDon);
            // them hoa don vao chi tiet su dung dich vu
            chiTietSuDungDichVuService.thanhToanChiTietSuDungDVCuaChiTietPhieuThue(idChiTietPhieuThue, soHoaDon);
        }
        return hoaDonResponse;
    }

    @Override
    public boolean doiPhong(int idChiTietPhieuThue, String maPhong) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(idChiTietPhieuThue);
        Phong phong = phongService.getPhongById(maPhong);
        if(thongTinHangPhongService.kiemTraPhongHangPhongTrong(phong.getHangPhong().getIdHangPhong(),chiTietPhieuThue.getNgayDen(),
                chiTietPhieuThue.getNgayDi(), 1)){
            if(phong.getHangPhong().getIdHangPhong() == chiTietPhieuThue.getPhong().getHangPhong().getIdHangPhong()) {
                chiTietPhieuThue.setPhong(phong);
                repository.save(chiTietPhieuThue);
                return true;
            }else{
                throw new Exception("Chỉ có thể đổi phòng thuộc cùng một hạng phòng!");
            }
        }
        return false;
    }

    @Override
    public List<ChiTietPhieuThueResponse> getChiTietPhieuThueResponseByIdPhieuThue(int idPhieuThue) throws Exception {
        List<ChiTietPhieuThue> chiTietPhieuThues = getChiTietPhieuThueByIdPhieuThue(idPhieuThue);
        List<ChiTietPhieuThueResponse> responses = new ArrayList<>();
        for (ChiTietPhieuThue chiTiet: chiTietPhieuThues) {
            responses.add(convertChiTietPhieuThueToResponse(chiTiet));
        }
        return responses;
    }

    public ChiTietPhieuThue themHoaDonToChiTietPhieuThue(Integer id, String soHoaDon) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(id);
        HoaDon hoaDon = hoaDonService.getHoaDonById(soHoaDon);
        chiTietPhieuThue.setHoaDon(hoaDon);
        return repository.save(chiTietPhieuThue);
    }

    public ChiTietPhieuThue thanhToanChiTietPhieuThue(Integer id) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(id);
        chiTietPhieuThue.setDaThanhToan(true);

        return repository.save(chiTietPhieuThue);
    }
}
