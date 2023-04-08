package com.example.javacursordtolombokmodelmapperhw9.web.rest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/")
public class Healthy {
    @GetMapping
    public void healthy(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        PrintWriter out = response.getWriter();
        out.println("{\"status\":\"ok\"}");
        out.close();
    }
}
