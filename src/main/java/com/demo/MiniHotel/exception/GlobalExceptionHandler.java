package com.demo.MiniHotel.exception;

import com.demo.MiniHotel.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
//    @ExceptionHandler
//    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception){
//        log.error(exception.getMessage());
//        ApiResponse apiResponse = ApiResponse.builder()
//                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
//                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
//                .build();
//        return ResponseEntity.badRequest().body(apiResponse);
//    }

    @ExceptionHandler
    ResponseEntity<ApiResponse> handlingValidException(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode;
        try{
            errorCode = ErrorCode.valueOf(enumKey);
        }catch (IllegalArgumentException e){
            errorCode = ErrorCode.KEY_INVALID;
        }

        ApiResponse apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ApiResponse apiResponse = ApiResponse.builder()
                .code(exception.getErrorCode().getCode())
                .message(exception.getErrorCode().getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }
}
