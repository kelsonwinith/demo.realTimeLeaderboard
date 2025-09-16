package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        private int statusCode;
        private long timestamp;

        public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(new ApiResponse<>(true, message, data, HttpStatus.OK.value(),
                                                System.currentTimeMillis()));
        }

        public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ApiResponse<>(true, message, data, HttpStatus.CREATED.value(),
                                                System.currentTimeMillis()));
        }

        public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse<>(false, message, null, HttpStatus.BAD_REQUEST.value(),
                                                System.currentTimeMillis()));
        }

        public static <T> ResponseEntity<ApiResponse<T>> unauthorized(String message) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(new ApiResponse<>(false, message, null, HttpStatus.UNAUTHORIZED.value(),
                                                System.currentTimeMillis()));
        }

        public static <T> ResponseEntity<ApiResponse<T>> forbidden(String message) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(new ApiResponse<>(false, message, null, HttpStatus.FORBIDDEN.value(),
                                                System.currentTimeMillis()));
        }

        public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponse<>(false, message, null, HttpStatus.NOT_FOUND.value(),
                                                System.currentTimeMillis()));
        }

        public static <T> ResponseEntity<ApiResponse<T>> error(String message) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse<>(false, message, null, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                System.currentTimeMillis()));
        }
}
