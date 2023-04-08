package com.example.javacursordtolombokmodelmapperhw9.repositories;


import com.example.javacursordtolombokmodelmapperhw9.entitys.Shop;
import org.springframework.data.repository.CrudRepository;

public interface ShopRepository extends CrudRepository<Shop, Long> {
}