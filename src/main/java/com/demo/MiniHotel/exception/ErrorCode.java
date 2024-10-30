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
    PHIEUDAT_NOT_CANCEL(1023, "Phiếu đặt phòng đã hoàn tất hoặc đã hủy"),
    DATA_IMPORT_NOT_VALID(1024, "Dữ liệu import không chính xác"),
    NGAYNHANPHONG_NOT_VALID(1025, "Chưa đến thời gian nhận phòng"),
    TIENGIAMGIA_NOT_VALID(1026, "Tiền giảm giá phải nhỏ hơn hoặc bằng tổng tiền phòng"),
    KHACHHANG_NOT_FOUND(1027, "Khách hàng không tồn tại"),
    KHACHHANG_DANGTHUE_EXISTED(1028, "Khách hàng này hiện đang lưu trú"),
    CHITIETKM_EXISTED(1029, "Hạng phòng này đã có trong chương trình khuyến mãi này"),
    HANGPHONG_KM_EXISTED(1030, "Hạng phòng này đang có trong một chương trình khuyến mãi khác tại cùng thời điểm"),
    CTKM_NOT_AVAILABLE(1030, "Chương trình khuyến mãi này đã kết thúc"),
    THOIGIAN_NOT_VALID(1031, "Thời gian chưa hợp lệ"),
    THOIGIAN_KM_INVALID(1032, "Thời gian khuyến mãi phải lớn hơn hiện tại"),
    TENHANGPHONG_EXISTED(1033, "Tên hạng phòng đã tồn tại"),
    HANGPHONG_EXISTED(1034, "Một hạng phòng khác đã được tạo trước đó với loại phòng và kiểu phòng này"),
    GIAPHONG_EXISTED(1035, "Bạn đã cập nhật giá của hạng phòng này ngày hôm nay"),
    NHANVIEN_AUTHORIZATION(1036, "Bạn không có quyền thực hiện hành động này"),
    MAPHONG_EXISTED(1037, "Mã phòng đã tồn tại"),
    PHONG_NOT_FOUND(1038, "Phòng không tồn tại"),
    CHITIETGIA_NOT_FOUND(1039, "Chi tiết thay đổi giá không tồn tại"),
    CHITIETGIA_EXISTED(1040, "Bạn đã thêm chi tiết thay đổi giá ngày hôm nay"),
    CHITIETGIA_EXISTED_2(1041, "Giá cho ngày áp dụng này đã được thêm trước đó"),
    CHITIETGIA_FINAL(1041, "Đây là chi tiết thay đổi giá cuối cùng còn lại, không thể xóa"),
    DICHVU_NOT_FOUND(1042, "Dịch vụ không tồn tại"),
    PHUTHU_NOT_FOUND(1042, "Phụ thu không tồn tại"),
    CHITIET_SUCCESS(1043, "Phòng này đã được thanh toán"),

    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
