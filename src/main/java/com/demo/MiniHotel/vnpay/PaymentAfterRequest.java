package com.demo.MiniHotel.vnpay;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentAfterRequest {
    int idPhieuDat;
    int luaChon;
    long tienTamUng;
}
