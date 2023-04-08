package com.example.javacursordtolombokmodelmapperhw9.web.rest;


import com.example.javacursordtolombokmodelmapperhw9.dtos.shops.ShopDto;
import com.example.javacursordtolombokmodelmapperhw9.entitys.Shop;
import com.example.javacursordtolombokmodelmapperhw9.exeptions.ShopNotFound;
import com.example.javacursordtolombokmodelmapperhw9.services.ShopService;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;


    @PostMapping
    public void createShop(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        Shop shop = gson.fromJson(json, Shop.class);

        try {
            Shop createdShop = shopService.createShop(shop);
            ShopDto createdShopDto = new ShopDto(createdShop.getId(), createdShop.getCity(), createdShop.getStreet(), createdShop.getName(), createdShop.isHasWebsite());
            String createdShopJson = gson.toJson(createdShopDto);
            response.setStatus(HttpStatus.CREATED.value());
            response.setContentType("application/json");
            response.getWriter().write(createdShopJson);
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteShop(@PathVariable Long id, HttpServletResponse response) throws IOException {
        try {
            shopService.deleteShop(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } catch (ShopNotFound e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(e.getMessage());
        }
    }

    @GetMapping
    public void getAllShops(HttpServletResponse response) throws IOException {
        try {
            List<Shop> shops = shopService.getAllShops();
            List<ShopDto> shopDtos = shops.stream()
                    .map(shop -> new ShopDto(shop.getId(), shop.getCity(), shop.getStreet(), shop.getName(), shop.isHasWebsite()))
                    .collect(Collectors.toList());
            Gson gson = new Gson();
            String shopsJson = gson.toJson(shopDtos);
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.getWriter().write(shopsJson);
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public void getShopById(@PathVariable Long id, HttpServletResponse response) throws IOException {
        try {
            Optional<Shop> shopOptional = shopService.getShopById(id);
            Gson gson = new Gson();
            shopOptional.ifPresent(shop -> {
                ShopDto shopDto = new ShopDto(shop.getId(), shop.getCity(), shop.getStreet(), shop.getName(), shop.isHasWebsite());
                String shopJson = gson.toJson(shopDto);
                try {
                    response.setStatus(HttpStatus.OK.value());
                    response.setContentType("application/json");
                    response.getWriter().write(shopJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            if (shopOptional.isEmpty()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.getWriter().write("Shop not found.");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void updateShop(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        Shop shop = gson.fromJson(json, Shop.class);

        try {
            Optional<Shop> updatedShopOptional = shopService.updateShop(id, shop);
            updatedShopOptional.ifPresent(updatedShop -> {
                ShopDto updatedShopDto = new ShopDto(updatedShop.getId(), updatedShop.getCity(), updatedShop.getStreet(), updatedShop.getName(), updatedShop.isHasWebsite());
                String updatedShopJson = gson.toJson(updatedShopDto);
                try {
                    response.setStatus(HttpStatus.OK.value());
                    response.setContentType("application/json");
                    response.getWriter().write(updatedShopJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            if (updatedShopOptional.isEmpty()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.getWriter().write("Shop not found.");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(e.getMessage());
        }
    }
}