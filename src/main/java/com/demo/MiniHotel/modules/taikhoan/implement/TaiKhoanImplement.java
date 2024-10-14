package com.demo.MiniHotel.modules.taikhoan.implement;

import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.model.NhanVien;
import com.demo.MiniHotel.model.NhomQuyen;
import com.demo.MiniHotel.model.TaiKhoan;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangResponse;
import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienResponse;
import com.demo.MiniHotel.modules.nhomquyen.service.INhomQuyenService;
import com.demo.MiniHotel.modules.taikhoan.dto.*;
import com.demo.MiniHotel.modules.taikhoan.exception.LoginWrongException;
import com.demo.MiniHotel.modules.taikhoan.service.ITaiKhoanService;
import com.demo.MiniHotel.repository.KhachHangRepository;
import com.demo.MiniHotel.repository.TaiKhoanRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaiKhoanImplement implements ITaiKhoanService {
    TaiKhoanRepository repository;
    INhomQuyenService nhomQuyenService;
    KhachHangRepository khachHangRepository;
    PasswordEncoder passwordEncoder;
    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @Override
    public TaiKhoan addNewTaiKhoan(TaiKhoanRequest request) throws Exception {
        Optional<TaiKhoan> taiKhoanOptional = repository.findByTenDangNhap(request.getTenDangNhap());
        if(taiKhoanOptional.isEmpty()) {
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setTenDangNhap(request.getTenDangNhap());
            taiKhoan.setMatKhau(request.getMatKhau());
            NhomQuyen nhomQuyen = nhomQuyenService.getNhomQuyenById(request.getIdNhomQuyen());
            taiKhoan.setNhomQuyen(nhomQuyen);

            return repository.save(taiKhoan);
        }

        throw new Exception("Tên đăng nhập đã tồn tại");
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<TaiKhoanResponse> getAllTaiKhoan() {
        log.info("Method get taikhoans");
        List<TaiKhoan> taiKhoans = repository.findAll();
        return taiKhoans.stream().map(
                        this::convertTaiKhoanResponse)
                .toList();
    }

    @Override
    public TaiKhoan getTaiKhoanById(Integer id) throws RuntimeException {
        Optional<TaiKhoan> taiKhoanOptional = repository.findById(id);
        if(taiKhoanOptional.isEmpty()){
            throw new RuntimeException("TaiKhoan not found!");
        }
        return taiKhoanOptional.get();
    }

    @Override
    public TaiKhoan updateMatKhau(TaiKhoanRequest request) throws Exception {
        Optional<TaiKhoan> taiKhoanOptional = repository.findByTenDangNhap(request.getTenDangNhap());
        if(taiKhoanOptional.isPresent()) {
            TaiKhoan taiKhoan = taiKhoanOptional.get();
            taiKhoan.setMatKhau(request.getMatKhau());
            return repository.save(taiKhoan);
        }

        throw new Exception("Tài khoản không tồn tại.");
    }

    @Override
    public void deleteTaiKhoan(Integer id) throws Exception {
        Optional<TaiKhoan> taiKhoanOptional = repository.findById(id);
        if(taiKhoanOptional.isEmpty()){
            throw new Exception("TaiKhoan not found!");
        }
        repository.deleteById(id);
    }

    @Override
    public TaiKhoan checkLogin(TaiKhoanRequest request) {
        Optional<TaiKhoan> taiKhoanOptional = repository.findByTenDangNhap(request.getTenDangNhap());
        if(taiKhoanOptional.isPresent()) {
            TaiKhoan taiKhoan = taiKhoanOptional.get();
            if(taiKhoan.getMatKhau().trim().equals(request.getMatKhau().trim()))
                return taiKhoan;
            else
                throw new LoginWrongException("Mật khẩu không chính xác");
        }

        throw new RuntimeException("Tài khoản không tồn tại.");
    }

    @Override
    public KhachHangResponse getKhachHangByTaiKhoan(String tenDangNhap, String matKhau) {
        TaiKhoanRequest request = new TaiKhoanRequest();
        request.setTenDangNhap(tenDangNhap);
        request.setMatKhau(matKhau);
        TaiKhoan taiKhoan = checkLogin(request);
        KhachHang khachHang = taiKhoan.getKhachHang();
        return convertKhachHangToResponse(khachHang);
    }

    @Override
    public NhanVienResponse getNhanVienByTaiKhoan(String tenDangNhap, String matKhau) {
        TaiKhoanRequest request = new TaiKhoanRequest();
        request.setTenDangNhap(tenDangNhap);
        request.setMatKhau(matKhau);
        TaiKhoan taiKhoan = checkLogin(request);
        NhanVien nhanVien = taiKhoan.getNhanVien();
        return convertNhanVienToResponse(nhanVien);
    }



    @Override
    public TaiKhoanKhachHangResponse dangKyTaiKhoan(DangKyRequest request) throws Exception {

        // TAI KHOAN
        TaiKhoanRequest taiKhoanRequest = new TaiKhoanRequest(request.getTenDangNhap(),
                request.getMatKhau(), 1);

        if(repository.existsByTenDangNhap(taiKhoanRequest.getTenDangNhap()))
            throw new AppException(ErrorCode.USER_EXISTED);

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTenDangNhap(request.getTenDangNhap());

        // Ma hoa Bcrypt
        taiKhoan.setMatKhau(passwordEncoder.encode(request.getMatKhau()));

        NhomQuyen nhomQuyen = nhomQuyenService.getNhomQuyenById(1); // Khách hàng
        taiKhoan.setNhomQuyen(nhomQuyen);

        // KHACH HANG
        if(khachHangRepository.existsByCmnd(request.getCmnd()))
            throw new AppException(ErrorCode.CCCD_EXISTED);

        if(khachHangRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        KhachHang khachHang = new KhachHang();
        khachHang.setCmnd(request.getCmnd());
        khachHang.setHoTen(request.getHoTen());
        khachHang.setSdt(request.getSdt());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setMaSoThue(null);
        khachHang.setEmail(request.getEmail());
        khachHang.setNgaySinh(request.getNgaySinh());
        khachHang.setGioiTinh(request.isGioiTinh());

        // VALIDATE thành công  => tạo
        TaiKhoan taiKhoanMoi = repository.save(taiKhoan);
        if(taiKhoanMoi.getIdTaiKhoan() != null){
            khachHang.setTaiKhoan(taiKhoanMoi);
        }
        KhachHang khachHangMoi = khachHangRepository.save(khachHang);

        return convertTaiKhoanKhachHangResponse(taiKhoanMoi, khachHangMoi);
    }

    @Override
    public LoginResponse authentication(LoginRequest request) {
        TaiKhoan taiKhoan = repository.findByTenDangNhap(request.getTenDangNhap())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authentication = passwordEncoder.matches(request.getMatKhau(), taiKhoan.getMatKhau());
        if(!authentication)
            throw new AppException(ErrorCode.AUTHENTICATED);

        String token = generateToken(taiKhoan);
        return LoginResponse.builder()
                .token(token)
                .authentication(true)
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        String token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        boolean verify = signedJWT.verify(verifier);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        return IntrospectResponse.builder()
                .valid(verify && expiryTime.after(new Date()))
                .build();
    }

    private String generateToken(TaiKhoan taiKhoan){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(taiKhoan.getTenDangNhap())
                .issuer("phamvanthanh.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", taiKhoan.getNhomQuyen().getTenNhomQuyen())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot generate token", e);
            throw new RuntimeException(e);
        }
    }

    private TaiKhoanResponse convertTaiKhoanResponse(TaiKhoan taiKhoan){
        return TaiKhoanResponse.builder()
                .tenDangNhap(taiKhoan.getTenDangNhap())
                .matKhau(taiKhoan.getMatKhau())
                .nhomQuyen(taiKhoan.getNhomQuyen().getTenNhomQuyen())
                .build();
    }

    private TaiKhoanKhachHangResponse convertTaiKhoanKhachHangResponse(TaiKhoan taiKhoan, KhachHang khachHang){
        return TaiKhoanKhachHangResponse.builder()
                .tenDangNhap(taiKhoan.getTenDangNhap())
//                .matKhau(taiKhoan.getMatKhau())
                .cmnd(khachHang.getCmnd())
                .hoTen(khachHang.getHoTen())
                .sdt(khachHang.getSdt())
                .email(khachHang.getEmail())
                .diaChi(khachHang.getDiaChi())
                .gioiTinh(khachHang.isGioiTinh())
                .ngaySinh(khachHang.getNgaySinh())
                .build();
    }

    private NhanVienResponse convertNhanVienToResponse(NhanVien nhanVien) {
        return new NhanVienResponse(nhanVien.getIdNhanVien(),
                nhanVien.getHoTen(), nhanVien.isGioiTinh(), nhanVien.getNgaySinh(),
                nhanVien.getSdt(), nhanVien.getEmail());
    }

    private KhachHangResponse convertKhachHangToResponse(KhachHang khachHang) {
        return new KhachHangResponse(khachHang.getIdKhachHang(), khachHang.getCmnd(),
                khachHang.getHoTen(), khachHang.getSdt(), khachHang.getDiaChi(), khachHang.getEmail(),
                khachHang.isGioiTinh(), khachHang.getNgaySinh()
        );
    }
}
