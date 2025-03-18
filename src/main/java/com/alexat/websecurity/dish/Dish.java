package com.alexat.websecurity.dish;


/*
@author   AlexAT
@project   websecurity
@class  Dish
@version  1.0.0
@since 18.03.2025 - 16.42
*/

import lombok.*;

import java.util.Objects;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Dish {
    private String id;
    private String name;
    private String description;

    private int calories;
    private double price;
    private String category;
    private String cuisine;
    private int preparationTime;

    public Dish(String name, String description, int calories, double price, String category, String cuisine, int preparationTime) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.price = price;
        this.category = category;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
