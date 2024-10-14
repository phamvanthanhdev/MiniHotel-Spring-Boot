package com.demo.MiniHotel.vnpay;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PaymentStatusDto implements Serializable {
    private String status;
    private String message;
    private String data;
}
