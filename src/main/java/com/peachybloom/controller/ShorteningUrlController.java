package com.peachybloom.controller;

import com.peachybloom.ApiResponse;
import com.peachybloom.ApiResponse.ApiResponseBuilder;
import com.peachybloom.domain.model.UrlConvertModel;
import com.peachybloom.service.ShorteningUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShorteningUrlController {

    private static final Logger logger = LoggerFactory.getLogger(ShorteningUrlController.class);

    @Autowired
    private ShorteningUrlService shorteningUrlService;

    @GetMapping("/")
    public String shorteningUrlform(Model model) {
        return "/form";
    }

    @ResponseBody
    @GetMapping("/convert")
    public ResponseEntity<ApiResponse<UrlConvertModel>> convert(@RequestParam String originUrl) {
        return ResponseEntity.ok(ApiResponseBuilder.builder()
                .data(shorteningUrlService.convert(originUrl))
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("/redirect")
    public String redirect(@RequestParam String location) {
        return "redirect:" + location;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> exceptionHandler(Exception e) {
        logger.error("==> catch exception : ", e);
        return ResponseEntity.ok(ApiResponseBuilder.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build());
    }
}
