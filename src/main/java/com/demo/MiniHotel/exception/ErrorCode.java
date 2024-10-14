package com.demo.MiniHotel.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    KEY_INVALID(1111, "Enum Key Invalid"),
    USER_EXISTED(1001, "Tên đăng nhập đã tồn tại"),
    CCCD_EXISTED(1002, "CCCD đã tồn tại"),
    EMAIL_EXISTED(1003, "Email đã tồn tại"),
    USERNAME_INVALID(1004, "Tên đăng nhập phải có ít nhất 6 kí tự"),
    PASSWORD_INVALID(1005, "Mật khẩu phải có ít nhất 8 kí tự"),
    CCCD_INVALID(1006, "CCCD bắt buộc phải có 12 kí tự"),
    NAME_INVALID(1007, "Họ tên không được bỏ trống"),
    SDT_INVALID(1008, "Số điện thoại phải có ít nhất 10 kí tự"),
    DIACHI_INVALID(1009, "Địa chỉ không được bỏ trống"),
    EMAIL_INVALID(1010, "Email chưa đúng định dạng"),
    USER_NOT_EXISTED(1011, "Tài khoản không tồn tại"),
    AUTHENTICATED(1012, "Lỗi xác thực"),
    NHOMQUYEN_NOT_FOUND(1013, "Nhóm quyền không tồn tại"),
    GIOITINH_INVALID(1014, "Giới tính không được bỏ trống"),
    NGAYSINH_INVALID(1015, "Ngày sinh không được bỏ trống"),
    UNAUTHORIZED(1016, "Không có quyền truy cập"),
    CCCD_NOT_EXISTED(1017, "CCCD không tồn tại"),
    HANGPHONG_NOT_ENOUGH(1018, "Hạng phòng không đủ số lượng trống"),
    CTPHIEUDAT_NOT_FOUND(1019, "Chi tiết phiếu đặt không tồn tại"),
    PHIEUTHUE_NOT_VALID(1020, "Phiếu đặt đã hoàn tất hoặc đã hủy"),
    PHONG_NOT_AVAIL(1021, "Phòng này đang được cho thuê"),
    PHIEUTHUE_NOT_FOUND(1022, "Phiếu thuê không tồn tại"),
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
