package com.demo.MiniHotel.modules.chitiet_phieuthue.implement;

import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.*;
import com.demo.MiniHotel.modules.chitiet_phieuthue.service.IChiTietPhieuThueService;
import com.demo.MiniHotel.modules.chitiet_phuthu.service.IChiTietPhuThuService;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.service.IChiTietSuDungDichVuService;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonResponse;
import com.demo.MiniHotel.modules.hoadon.service.IHoaDonService;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import com.demo.MiniHotel.modules.phieuthuephong.dto.ChiTietKhachThueRequest;
import com.demo.MiniHotel.modules.phieuthuephong.dto.DelChiTietKhachThueRequest;
import com.demo.MiniHotel.modules.phong.service.IPhongService;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import com.demo.MiniHotel.modules.thongtin_phong.dto.ThongTinPhongHienTaiResponse;
import com.demo.MiniHotel.modules.thongtin_phong.service.IThongTinPhongService;
import com.demo.MiniHotel.repository.ChiTietPhieuThueRepository;
import com.demo.MiniHotel.repository.HoaDonRepository;
import com.demo.MiniHotel.repository.PhieuThuePhongRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
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
    @Autowired
    private EntityManager entityManager;
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
        chiTietPhieuThue.setTienGiamGia(request.getTienGiamGia());

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
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(khachThueRequest.getIdChiTietPhieuThue());
        List<KhachHang> khachHangList = chiTietPhieuThue.getKhachHangs();

        // Lấy danh sách khách thuê hiện tại
        List<ThongTinPhongHienTaiResponse> thongTinPhongHienTais = thongTinPhongService.getThongTinPhongHienTai();
        List<KhachHang> khachHangDangThues = new ArrayList<>();
        for (ThongTinPhongHienTaiResponse thongTinPhong:thongTinPhongHienTais) {
            if(thongTinPhong.getIdChiTietPhieuThue() != null) {
                List<KhachHang> khachHangs = getChiTietPhieuThueById(thongTinPhong.getIdChiTietPhieuThue()).getKhachHangs();
                khachHangDangThues.addAll(khachHangs);
            }
        }

        // Nếu khách hàng hiện tại đang lưu trú thì không cho thêm nữa
        List<KhachHang> khachHangs = new ArrayList<>();
        for (Integer idKhachHang: khachThueRequest.getIdKhachThues()) {
            if(khachHangDangThues.contains(khachHangService.getKhachHangById(idKhachHang)))
                throw new AppException(ErrorCode.KHACHHANG_DANGTHUE_EXISTED);
            khachHangs.add(khachHangService.getKhachHangById(idKhachHang));
        }


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
                            chiTietPhieuThue.getIdChiTietPhieuThue(),
                            khachHang.getIdKhachHang(),
                            thongTinPhong.getMaPhong(), khachHang.getHoTen(),
                            khachHang.getCmnd(),
                            khachHang.getSdt(),
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

//        //tao mot hoa don moi
//        HoaDonResponse hoaDonResponse = hoaDonService.themHoaDonMoi(tongTien);
//        String soHoaDon = hoaDonResponse.getSoHoaDon();
//        //them hoa don vao chi tiet phieu thue
//        themHoaDonToChiTietPhieuThue(idChiTietPhieuThue, hoaDonResponse.getSoHoaDon());
//        //dat da thanh toan = true
//        thanhToanChiTietPhieuThue(idChiTietPhieuThue);
//        //them hoa don vao chi tiet phu thu và chuyển da thanh toan = true
//        phuThuService.thanhToanChiTietPhuThuCuaChiTietPhieuThue(idChiTietPhieuThue, soHoaDon);
//        // them hoa don vao chi tiet su dung dich vu
//        chiTietSuDungDichVuService.thanhToanChiTietSuDungDVCuaChiTietPhieuThue(idChiTietPhieuThue, soHoaDon);
//        return hoaDonResponse;
        return new HoaDonResponse();
    }

    @Override
    public HoaDonResponse traPhongKhachSanKhachDoan(TraPhongRequest request) throws Exception {
        //tao mot hoa don moi
//        HoaDonResponse hoaDonResponse = hoaDonService.themHoaDonMoi(request.getThucThu(), request.getIdPhieuThue());
//        String soHoaDon = hoaDonResponse.getSoHoaDon();
//        for (int idChiTietPhieuThue: request.getIdChiTietPhieuThues()) {
//            //them hoa don vao chi tiet phieu thue
//            themHoaDonToChiTietPhieuThue(idChiTietPhieuThue, hoaDonResponse.getSoHoaDon());
//            //dat da thanh toan = true
//            thanhToanChiTietPhieuThue(idChiTietPhieuThue);
//            //them hoa don vao chi tiet phu thu và chuyển da thanh toan = true
//            phuThuService.thanhToanChiTietPhuThuCuaChiTietPhieuThue(idChiTietPhieuThue, soHoaDon);
//            // them hoa don vao chi tiet su dung dich vu
//            chiTietSuDungDichVuService.thanhToanChiTietSuDungDVCuaChiTietPhieuThue(idChiTietPhieuThue, soHoaDon);
//        }
        return new HoaDonResponse();
    }

    @Override
    public boolean doiPhong(int idChiTietPhieuThue, String maPhong) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(idChiTietPhieuThue);
        Phong phong = phongService.getPhongById(maPhong);
        if(thongTinHangPhongService.kiemTraPhongHangPhongTrong(phong.getHangPhong().getIdHangPhong(),chiTietPhieuThue.getNgayDen(),
                chiTietPhieuThue.getNgayDi(), 1)){
            if(Objects.equals(phong.getHangPhong().getIdHangPhong(), chiTietPhieuThue.getPhong().getHangPhong().getIdHangPhong())) {
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
    public ChiTietPhieuThue themChiTietPhieuThue(ChiTietPhieuThueRequest request) throws Exception {
        if(thongTinPhongService.kiemTraPhongThue(request.getNgayDen(), request.getNgayDi(), request.getMaPhong()))
            throw new AppException(ErrorCode.PHONG_NOT_AVAIL);

        //kiểm tra số lượng hạng phòng trống
        Phong phong = phongService.getPhongById(request.getMaPhong());
        int soLuongTrong = thongTinHangPhongService.laySoLuongHangPhongTrong(request.getNgayDen(), request.getNgayDi(), phong.getHangPhong().getIdHangPhong());
        if(soLuongTrong < 1){
            throw new AppException(ErrorCode.HANGPHONG_NOT_ENOUGH);
        }

        ChiTietPhieuThue chiTietPhieuThue = new ChiTietPhieuThue();


        PhieuThuePhong phieuThuePhong = PhieuThuePhongRepository.findById(request.getIdPhieuThue())
                .orElseThrow(() -> new AppException(ErrorCode.PHIEUTHUE_NOT_FOUND));

        chiTietPhieuThue.setPhieuThuePhong(phieuThuePhong);
        chiTietPhieuThue.setPhong(phong);

        chiTietPhieuThue.setNgayDen(request.getNgayDen());
        chiTietPhieuThue.setNgayDi(request.getNgayDi());
        chiTietPhieuThue.setDonGia(request.getDonGia());
        chiTietPhieuThue.setDaThanhToan(false);

        long soNgayThue;
        if(request.getNgayDen().equals(request.getNgayDi()))
            soNgayThue = 1;
        else
            soNgayThue = ChronoUnit.DAYS.between(request.getNgayDen(), request.getNgayDi());

        long giaPhong = request.getDonGia() * soNgayThue;
        if(request.getTienGiamGia() > giaPhong)
            throw new AppException(ErrorCode.TIENGIAMGIA_NOT_VALID);

        chiTietPhieuThue.setTienGiamGia(request.getTienGiamGia());

        return repository.save(chiTietPhieuThue);
    }

    @Override
    public ChiTietPhieuThueResponse thayDoiNgayTraPhong(Integer id, LocalDate ngayTraPhong) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(id);

        if(chiTietPhieuThue.getNgayDi().isBefore(ngayTraPhong)) {
            // Kiểm tra phòng có trống trong khoảng từ ngày trả phòng cũ đến ngày trả phòng mới
            if (thongTinPhongService.kiemTraPhongThue(
                    chiTietPhieuThue.getNgayDi().plusDays(1),
                    ngayTraPhong,
                    chiTietPhieuThue.getPhong().getMaPhong()))
                throw new AppException(ErrorCode.PHONG_NOT_AVAIL);

            // Kiểm tra hạng phòng còn trống không trong khoảng từ ngày trả phòng cũ đến ngày trả phòng mới
            if (!thongTinHangPhongService.kiemTraPhongHangPhongTrong(
                    chiTietPhieuThue.getPhong().getHangPhong().getIdHangPhong(),
                    chiTietPhieuThue.getNgayDi().plusDays(1),
                    ngayTraPhong, 1))
                throw new AppException(ErrorCode.HANGPHONG_NOT_ENOUGH);
        }

        chiTietPhieuThue.setNgayDi(ngayTraPhong);
        return convertChiTietPhieuThueToResponse(repository.save(chiTietPhieuThue));
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

    @Override
    public ChiTietPhieuThue themHoaDonToChiTietPhieuThue(Integer id, String soHoaDon) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(id);
        HoaDon hoaDon = hoaDonService.getHoaDonById(soHoaDon);
        chiTietPhieuThue.setHoaDon(hoaDon);
        chiTietPhieuThue.setDaThanhToan(true);
        return repository.save(chiTietPhieuThue);
    }

    public ChiTietPhieuThue thanhToanChiTietPhieuThue(Integer id) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(id);
        chiTietPhieuThue.setDaThanhToan(true);

        return repository.save(chiTietPhieuThue);
    }

    @Override
    public ChiTietPhieuThueResponse capNhatNgayTraPhong(Integer id, LocalDate ngayTraPhong) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(id);
        chiTietPhieuThue.setNgayDi(ngayTraPhong);
        return convertChiTietPhieuThueToResponse(repository.save(chiTietPhieuThue));
    }

    @Override
    public ChiTietPhieuThueResponse capNhatTienGiamGia(Integer id, long tienGiamGia) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(id);
        if(chiTietPhieuThue.getDaThanhToan()){
            throw new AppException(ErrorCode.CHITIET_SUCCESS);
        }

        long soNgayThue = ChronoUnit.DAYS.between(chiTietPhieuThue.getNgayDen(), chiTietPhieuThue.getNgayDi());
        if(tienGiamGia > chiTietPhieuThue.getDonGia() * soNgayThue)
            throw new AppException(ErrorCode.TIENGIAMGIA_NOT_VALID);

        chiTietPhieuThue.setTienGiamGia(tienGiamGia);

        return convertChiTietPhieuThueToResponse(repository.save(chiTietPhieuThue));
    }

    @Override
    public List<ThongKeTanSuatResponse> thongKeTanSuat(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws Exception {
        List<TanSuatThuePhongResponse> tanSuatThuePhongRespons = new ArrayList<>();
        long soNgayThongKe = ChronoUnit.DAYS.between(ngayBatDau, ngayKetThuc) + 1;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_EVEN);

        LocalDate currentDate = ngayBatDau;
        while (!currentDate.isAfter(ngayKetThuc)) {
            entityManager.clear();
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("KiemTraPhongDaThueTheoNgay", PhongDaThue.class)
                    .registerStoredProcedureParameter("ngay", LocalDate.class, ParameterMode.IN)
                    .setParameter("ngay", currentDate);
            List<PhongDaThue> phongDaThues = query.getResultList();

            if(tanSuatThuePhongRespons.size() == 0){
                for (PhongDaThue phongDaThue: phongDaThues) {
                    tanSuatThuePhongRespons.add(TanSuatThuePhongResponse.builder()
                            .maPhong(phongDaThue.getMaPhong())
                            .idHangPhong(phongDaThue.getIdHangPhong())
                            .tanSuat(phongDaThue.getDaThue() ? 1 : 0)
                                    .tiLe("0")
                            .build());
                }
            } else{
                for (TanSuatThuePhongResponse tanSuatThuePhongResponse : tanSuatThuePhongRespons) {
                    for (PhongDaThue phongDaThue: phongDaThues) {
                        if (phongDaThue.getMaPhong().equals(tanSuatThuePhongResponse.getMaPhong())
                                && phongDaThue.getDaThue()) {
                            tanSuatThuePhongResponse.setTanSuat(tanSuatThuePhongResponse.getTanSuat() + 1);

                            double tiLePhong = (double) tanSuatThuePhongResponse.getTanSuat() /soNgayThongKe * 100;
                            tanSuatThuePhongResponse.setTiLe(df.format(tiLePhong));
                        }
                    }
                }
            }

            currentDate = currentDate.plusDays(1);
        }

        List<ThongKeTanSuatResponse> thongKeTanSuatResponses = new ArrayList<>();
        // Vì hạng phòng đã được sắp xếp
        int tanSuatHangPhong = 0;
        int index = 0;
        int tongTanSuatHangPhong = 0;

        for (int i = 0; i < tanSuatThuePhongRespons.size(); i++) {
            tanSuatHangPhong += tanSuatThuePhongRespons.get(i).getTanSuat(); // Tính tần suất
            double tiLeHangPhong = (double) tanSuatHangPhong / soNgayThongKe * 100;
//            tongTanSuatHangPhong += tanSuatThuePhongRespons.get(i).getTanSuat(); // Tổng tần suất tất cả
            if(i == tanSuatThuePhongRespons.size() - 1){ // Trường hợp phân tử cuối cùng trong danh sách
                ThongTinHangPhong thongTinHangPhong = thongTinHangPhongService
                        .getThongTinHangPhongById(tanSuatThuePhongRespons.get(i).getIdHangPhong());
                // Thêm tỉ lệ của từng phòng
                /*for (int j = index; j < i+1; j++) {
                    double tiLePhong = 0;
                    if(tanSuatHangPhong > 0)
                        tiLePhong = (double) tanSuatThuePhongRespons.get(j).getTanSuat() / tanSuatHangPhong * 100;
                    tanSuatThuePhongRespons.get(j).setTiLe(df.format(tiLePhong));
                }*/

                // Thêm thống kê tần suất
                thongKeTanSuatResponses.add(ThongKeTanSuatResponse.builder()
                        .idHangPhong(tanSuatThuePhongRespons.get(i).getIdHangPhong())
                        .tenHangPhong(thongTinHangPhong.getTenHangPhong())
                        .tanSuat(tanSuatHangPhong)
                        .tiLe(df.format(tiLeHangPhong))
                        .soNgayThongKe(soNgayThongKe)
                        .tanSuatThuePhongs(tanSuatThuePhongRespons.subList(index, i + 1))
                        .build());
            }else{
                if(tanSuatThuePhongRespons.get(i).getIdHangPhong() != tanSuatThuePhongRespons.get(i+1).getIdHangPhong()){
                    ThongTinHangPhong thongTinHangPhong = thongTinHangPhongService
                            .getThongTinHangPhongById(tanSuatThuePhongRespons.get(i).getIdHangPhong());

//                    // Thêm tỉ lệ của từng phòng
//                    for (int j = index; j < i+1; j++) {
//                        double tiLePhong = 0;
//                        if(tanSuatHangPhong > 0)
//                            tiLePhong = (double) tanSuatThuePhongRespons.get(j).getTanSuat() / tanSuatHangPhong * 100;
//                        tanSuatThuePhongRespons.get(j).setTiLe(df.format(tiLePhong));
//                    }

                    thongKeTanSuatResponses.add(ThongKeTanSuatResponse.builder()
                            .idHangPhong(tanSuatThuePhongRespons.get(i).getIdHangPhong())
                            .tenHangPhong(thongTinHangPhong.getTenHangPhong())
                            .tanSuat(tanSuatHangPhong)
                            .tiLe(df.format(tiLeHangPhong))
                            .soNgayThongKe(soNgayThongKe)
                            .tanSuatThuePhongs(tanSuatThuePhongRespons.subList(index, i + 1))
                            .build());
                    tanSuatHangPhong = 0;
                    index = i + 1;
                }
            }
        }

//        for (ThongKeTanSuatResponse response: thongKeTanSuatResponses) {
//            double tiLeHangPhong = (double) response.getTanSuat() / tongTanSuatHangPhong * 100;
//            response.setTiLe(df.format(tiLeHangPhong));
//        }


        return thongKeTanSuatResponses;
    }

    @Override
    public ChiTietPhieuThue capNhatChiTietPhieuThue(CapNhatChiTietPhieuThueRequest request) throws Exception {
        ChiTietPhieuThue chiTietPhieuThue = getChiTietPhieuThueById(request.getIdChiTietPhieuThue());
        if(request.getNgayTraPhong().isBefore(chiTietPhieuThue.getNgayDen()))
            throw new AppException(ErrorCode.THOIGIAN_NOT_VALID);

        if(!request.getNgayTraPhong().isEqual(chiTietPhieuThue.getNgayDi())){
            if(request.getNgayTraPhong().isBefore(chiTietPhieuThue.getNgayDi())){
                chiTietPhieuThue.setNgayDi(request.getNgayTraPhong());
            }else{
                boolean isHangPhongTrong = thongTinHangPhongService.kiemTraPhongHangPhongTrong(
                        chiTietPhieuThue.getPhong().getHangPhong().getIdHangPhong(),
                        chiTietPhieuThue.getNgayDen(), request.getNgayTraPhong(), 1);
                if(!isHangPhongTrong)
                    throw new AppException(ErrorCode.HANGPHONG_NOT_ENOUGH);
                chiTietPhieuThue.setNgayDi(request.getNgayTraPhong());
            }
        }

        chiTietPhieuThue.setTienGiamGia(request.getTienGiamGia());

        return repository.save(chiTietPhieuThue);
    }

    private ChiTietPhieuThueResponse convertChiTietPhieuThueToResponse(ChiTietPhieuThue chiTietPhieuThue) throws Exception {
        String tenHangPhong = chiTietPhieuThue.getPhong().getHangPhong().getTenHangPhong();
        long ngay;
        //Nếu ngày đến và ngày trả phòng là cùng một ngày => tính 1 ngày
        if(chiTietPhieuThue.getNgayDen().equals(chiTietPhieuThue.getNgayDi()))
            ngay = 1;
        else
            ngay = ChronoUnit.DAYS.between(chiTietPhieuThue.getNgayDen(), chiTietPhieuThue.getNgayDi());

        long tongTienPhong = chiTietPhieuThue.getDonGia() * ngay - chiTietPhieuThue.getTienGiamGia();
        if(tongTienPhong < 0) tongTienPhong = 0;
        long tongTienDichVu = chiTietSuDungDichVuService.getTongTienChiTietSuDungDichVu(chiTietPhieuThue.getIdChiTietPhieuThue());
        long tongTienPhuThu = phuThuService.getTongTienChiTietPhuThu(chiTietPhieuThue.getIdChiTietPhieuThue());
        long tongTienTatCa = chiTietPhieuThue.getDonGia() * ngay + tongTienDichVu + tongTienPhuThu - chiTietPhieuThue.getTienGiamGia();

        int idHangPhong = chiTietPhieuThue.getPhong().getHangPhong().getIdHangPhong();
        return new ChiTietPhieuThueResponse(chiTietPhieuThue.getIdChiTietPhieuThue(),
                chiTietPhieuThue.getPhong().getMaPhong(), idHangPhong, tenHangPhong,
                chiTietPhieuThue.getPhieuThuePhong().getIdPhieuThue(),
                chiTietPhieuThue.getNgayDen(), chiTietPhieuThue.getNgayDi(),
                chiTietPhieuThue.getDonGia(),
                chiTietPhieuThue.getDaThanhToan(),
                chiTietPhieuThue.getTienGiamGia(),
                tongTienPhong,
                tongTienDichVu,
                tongTienPhuThu,
                tongTienTatCa);
    }
}
