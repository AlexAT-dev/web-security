package com.alexat.websecurity.dish;


/*
@author   AlexAT
@project   websecurity
@class  DishService
@version  1.0.0
@since 18.03.2025 - 16.51
*/

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DishService {

    private final DishRepository repository;

    private List<Dish> dishes;

    @PostConstruct
    void init() {
        dishes.add(new Dish("1", "Pizza", "Cheese and tomato pizza", 800, 9.99, "Fast Food", "Italian", 20));
        dishes.add(new Dish("2", "Burger", "Beef burger with fries", 950, 11.99, "Fast Food", "American", 15));
        dishes.add(new Dish("3", "Salad", "Fresh vegetable salad", 350, 5.49, "Healthy", "Mediterranean", 10));
        dishes.add(new Dish("4", "Pasta", "Spaghetti with marinara sauce", 650, 7.99, "Main Course", "Italian", 25));
        dishes.add(new Dish("5", "Sushi", "Salmon sushi rolls", 500, 12.49, "Seafood", "Japanese", 30));

        repository.saveAll(dishes);
    }

    public List<Dish> getAll() {
        return repository.findAll();
    }

    public Dish getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Dish create(Dish Dish) {
        return repository.save(Dish);
    }

    public  Dish update(Dish Dish) {
        return repository.save(Dish);
    }

    public void delById(String id) {
        repository.deleteById(id);
    }


}