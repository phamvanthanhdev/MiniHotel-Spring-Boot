package com.demo.MiniHotel.modules.khachhang.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileUploadResponse {
    String tenFile;
    String tenFileOriginal;
    @JsonIgnore
    LocalDateTime thoiGianOriginal;
    String thoiGian;
    int idPhieuDat;
}
