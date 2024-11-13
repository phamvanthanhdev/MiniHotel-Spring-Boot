package com.demo.MiniHotel.modules.nhomquyen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhomQuyenResponse {
    private int idNhomQuyen;
    private String tenNhomQuyen;
}
