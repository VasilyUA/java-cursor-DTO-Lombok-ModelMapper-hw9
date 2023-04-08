package com.example.javacursordtolombokmodelmapperhw9.services.impl;

import com.example.javacursordtolombokmodelmapperhw9.entitys.Shop;
import com.example.javacursordtolombokmodelmapperhw9.exeptions.ShopNotFound;
import com.example.javacursordtolombokmodelmapperhw9.repositories.ShopRepository;
import com.example.javacursordtolombokmodelmapperhw9.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public Shop createShop(Shop shop) {
        return shopRepository.save(shop);
    }

    @Override
    public void deleteShop(Long id) {
        Optional<Shop> shopOptional = getShopById(id);
        if (shopOptional.isPresent()) {
            shopRepository.deleteById(id);
        } else {
            throw new ShopNotFound("Shop not found");
        }
    }

    @Override
    public List<Shop> getAllShops() {
        return (List<Shop>) shopRepository.findAll();
    }

    @Override
    public Optional<Shop> getShopById(Long id) {
        return shopRepository.findById(id);
    }

    @Override
    public Optional<Shop> updateShop(Long id, Shop shopUpdate) {
        Optional<Shop> shopOptional = shopRepository.findById(id);
        if (shopOptional.isPresent()) {
            Shop existingShop = shopOptional.get();
            existingShop.setCity(shopUpdate.getCity());
            existingShop.setStreet(shopUpdate.getStreet());
            existingShop.setName(shopUpdate.getName());
            existingShop.setNumberOfEmployees(shopUpdate.getNumberOfEmployees());
            existingShop.setHasWebsite(shopUpdate.isHasWebsite());
            Shop updatedShop = shopRepository.save(existingShop);
            return Optional.of(updatedShop);
        }
        return Optional.empty();
    }
}