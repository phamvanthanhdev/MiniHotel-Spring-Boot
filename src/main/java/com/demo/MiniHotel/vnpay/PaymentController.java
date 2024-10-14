package com.demo.MiniHotel.vnpay;

import com.demo.MiniHotel.model.PhieuDatPhong;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatDetailsResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatResponse;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class PaymentController {
    private final IPhieuDatService phieuDatService;

    @PostMapping("/create_payment")
    public ResponseEntity<?> createPayment(
            @RequestBody PhieuDatThanhToanRequest request
    ) throws Exception {
        PhieuDatPhong phieuDat = phieuDatService.datPhongKhachSan(request);

//        long amount = Integer.parseInt(req.getParameter("amount"))*100;
        long amount = request.getPhieuDat().getTienTamUng()*100;
//        String bankCode = req.getParameter("bankCode");

        String vnp_TxnRef = String.valueOf(phieuDat.getIdPhieuDat());
//        String vnp_TxnRef = ConfigVnpay.getRandomNumber(8);
//        String vnp_IpAddr = ConfigVnpay.getIpAddress(req);

        String vnp_TmnCode = ConfigVnpay.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", ConfigVnpay.vnp_Version);
        vnp_Params.put("vnp_Command", ConfigVnpay.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_IpAddr", "27.78.79.140");
        vnp_Params.put("vnp_OrderType", ConfigVnpay.orderType);

//        String locate = req.getParameter("language");
//        if (locate != null && !locate.isEmpty()) {
//            vnp_Params.put("vnp_Locale", locate);
//        } else {
//            vnp_Params.put("vnp_Locale", "vn");
//        }
        vnp_Params.put("vnp_ReturnUrl", ConfigVnpay.vnp_ReturnUrl);
//        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = ConfigVnpay.hmacSHA512(ConfigVnpay.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = ConfigVnpay.vnp_PayUrl + "?" + queryUrl;

        PaymentResDto paymentResDto = new PaymentResDto();
        paymentResDto.setStatus("OK");
        paymentResDto.setMessage("Successfully");
        paymentResDto.setType("Present"); // Thanh toán tại thời điểm đặt
        paymentResDto.setURL(paymentUrl);

        return new ResponseEntity<>(paymentResDto, HttpStatus.OK);

//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));
    }

    @GetMapping("/payment-update")
    public ResponseEntity<?> transaction(
            @RequestParam("vnp_Amount") String vnp_Amount,
            @RequestParam("vnp_PayDate") String vnp_PayDate,
            @RequestParam("vnp_ResponseCode") String vnp_ResponseCode,
            @RequestParam("vnp_TxnRef") String vnp_TxnRef
    ) throws Exception {
        PaymentStatusDto paymentStatusDto = new PaymentStatusDto();
        if(vnp_ResponseCode.equals("00")){
            phieuDatService.thanhToanPhieuDat(Integer.parseInt(vnp_TxnRef), Long.parseLong(vnp_Amount));

            paymentStatusDto.setStatus("OK");
            paymentStatusDto.setMessage("Successfully");
            paymentStatusDto.setData("Amount: " + Long.parseLong(vnp_Amount)
                    + "; vnp_PayDate: " + vnp_PayDate);
        }else{
            paymentStatusDto.setStatus("Error");
            paymentStatusDto.setMessage("Failure");
            paymentStatusDto.setData("");
        }
        return new ResponseEntity<>(paymentStatusDto, HttpStatus.OK);
    }

    // Thanh toán phần còn lại của đơn hàng
    @PostMapping("/create_payment/after")
    public ResponseEntity<?> createPayment(
            @RequestParam int idPhieuDat
    ) throws Exception {
        PhieuDatDetailsResponse phieuDat = phieuDatService.getPhieuDatDetailsById(idPhieuDat);

        long amount = (phieuDat.getTongTien() - phieuDat.getTienTamUng())*100;

        String vnp_TxnRef = "PD" + phieuDat.getIdPhieuDat();


        String vnp_TmnCode = ConfigVnpay.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", ConfigVnpay.vnp_Version);
        vnp_Params.put("vnp_Command", ConfigVnpay.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_IpAddr", "27.78.79.140");
        vnp_Params.put("vnp_OrderType", ConfigVnpay.orderType);

//        String locate = req.getParameter("language");
//        if (locate != null && !locate.isEmpty()) {
//            vnp_Params.put("vnp_Locale", locate);
//        } else {
//            vnp_Params.put("vnp_Locale", "vn");
//        }
        vnp_Params.put("vnp_ReturnUrl", ConfigVnpay.vnp_ReturnUrl_After);
//        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = ConfigVnpay.hmacSHA512(ConfigVnpay.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = ConfigVnpay.vnp_PayUrl + "?" + queryUrl;

        PaymentResDto paymentResDto = new PaymentResDto();
        paymentResDto.setStatus("OK");
        paymentResDto.setMessage("Successfully");
        paymentResDto.setType("After"); // Thanh toán sau thời điểm đặt
        paymentResDto.setURL(paymentUrl);

        return new ResponseEntity<>(paymentResDto, HttpStatus.OK);
    }

    @GetMapping("/payment-update/after")
    public ResponseEntity<?> transactionAfter(
            @RequestParam("vnp_Amount") String vnp_Amount,
            @RequestParam("vnp_PayDate") String vnp_PayDate,
            @RequestParam("vnp_ResponseCode") String vnp_ResponseCode,
            @RequestParam("vnp_TxnRef") String vnp_TxnRef
    ) throws Exception {
        PaymentStatusDto paymentStatusDto = new PaymentStatusDto();
        if(vnp_ResponseCode.equals("00")){
            PhieuDatPhong phieuDat = phieuDatService.getPhieuDatPhongById(Integer.parseInt(vnp_TxnRef));
            long amount = Long.parseLong(vnp_Amount) + phieuDat.getTienTamUng();
            phieuDatService.thanhToanPhieuDat(Integer.parseInt(vnp_TxnRef), amount);

            paymentStatusDto.setStatus("OK");
            paymentStatusDto.setMessage("Successfully");
            paymentStatusDto.setData("Amount: " + Long.parseLong(vnp_Amount)
                    + "; vnp_PayDate: " + vnp_PayDate);
        }else{
            paymentStatusDto.setStatus("Error");
            paymentStatusDto.setMessage("Failure");
            paymentStatusDto.setData("");
        }
        return new ResponseEntity<>(paymentStatusDto, HttpStatus.OK);
    }
}
