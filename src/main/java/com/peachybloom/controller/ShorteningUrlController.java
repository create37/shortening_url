package com.peachybloom.controller;

import com.peachybloom.domain.model.UrlConvertModel;
import com.peachybloom.service.ShorteningUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShorteningUrlController {

    @Autowired
    private ShorteningUrlService shorteningUrlService;

    @GetMapping("/")
    public String shorteningUrlform(Model model) {
        return "/form";
    }

    @ResponseBody
    @GetMapping("/convert")
    public ResponseEntity<UrlConvertModel> convert(@RequestParam String originUrl) {
        return ResponseEntity.ok(shorteningUrlService.convert(originUrl));
    }

    @GetMapping("/redirect")
    public String redirect(@RequestParam String location) {
        return "redirect:" + location;
    }
}
