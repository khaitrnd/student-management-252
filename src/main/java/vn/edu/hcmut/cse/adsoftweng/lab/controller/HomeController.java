package vn.edu.hcmut.cse.adsoftweng.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    // Khi người dùng truy cập trang chủ "/", tự động điều hướng sang "/students"
    @GetMapping("/")
    public String home() {
        return "redirect:/students";
    }
}