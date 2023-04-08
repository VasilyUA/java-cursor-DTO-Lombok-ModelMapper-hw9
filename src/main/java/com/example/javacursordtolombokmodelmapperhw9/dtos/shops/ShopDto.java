package com.example.javacursordtolombokmodelmapperhw9.dtos.shops;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ShopDto {
    private Long id;
    private String city;
    private String street;
    private String name;
    private boolean hasWebsite;
}
