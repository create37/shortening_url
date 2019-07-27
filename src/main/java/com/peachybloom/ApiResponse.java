package com.peachybloom;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private T data;

    private HttpStatus status;

    private String message;

    private ApiResponse() {}

    public T getData() {
        return data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static class ApiResponseBuilder<T> {
        private T data;
        private HttpStatus status;
        private String message;

        public static ApiResponseBuilder builder() {
            return new ApiResponseBuilder<>();
        }

        public ApiResponseBuilder data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ApiResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ApiResponse build() {
            ApiResponse<T> apiResponse = new ApiResponse<>();
            apiResponse.data = data;
            apiResponse.status = status;
            apiResponse.message = message;
            return apiResponse;
        }
    }
}
