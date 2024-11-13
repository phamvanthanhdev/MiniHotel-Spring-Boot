package com.demo.MiniHotel.configuration;

import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.model.NhomQuyen;
import com.demo.MiniHotel.model.TaiKhoan;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
import com.demo.MiniHotel.repository.KhachHangRepository;
import com.demo.MiniHotel.repository.NhomQuyenRepository;
import com.demo.MiniHotel.repository.TaiKhoanRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    IPhieuDatService phieuDatService;

    @Bean
    ApplicationRunner applicationRunner(TaiKhoanRepository taiKhoanRepository,
                                        NhomQuyenRepository nhomQuyenRepository){
        return args -> {
            if(taiKhoanRepository.findByTenDangNhap("admin").isEmpty()){
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setTenDangNhap("admin");
                taiKhoan.setMatKhau(passwordEncoder.encode("admin"));
                NhomQuyen nhomQuyen = nhomQuyenRepository.findByTenNhomQuyen("ADMIN").orElseThrow(() ->
                        new AppException(ErrorCode.NHOMQUYEN_NOT_FOUND));
                taiKhoan.setNhomQuyen(nhomQuyen);
                taiKhoanRepository.save(taiKhoan);

                log.info("Tài khoản admin được thêm với mật khẩu mặc định(admin), hãy thay đổi nó");
            }

            // Tự động
            phieuDatService.tuDongHuyPhieuDats();
        };
    }
}
