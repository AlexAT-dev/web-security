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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    private List<Dish> dishes;

    @PostConstruct
    void init() {
        dishes.add(new Dish("1", "Pizza", "Cheese and tomato pizza", 800, 9.99, "Fast Food", "Italian", 20));
        dishes.add(new Dish("2", "Burger", "Beef burger with fries", 950, 11.99, "Fast Food", "American", 15));
        dishes.add(new Dish("3", "Salad", "Fresh vegetable salad", 350, 5.49, "Healthy", "Mediterranean", 10));
        dishes.add(new Dish("4", "Pasta", "Spaghetti with marinara sauce", 650, 7.99, "Main Course", "Italian", 25));
        dishes.add(new Dish("5", "Sushi", "Salmon sushi rolls", 500, 12.49, "Seafood", "Japanese", 30));

        dishRepository.saveAll(dishes);
    }

    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    public Dish getById(String id) {
        return dishRepository.findById(id).orElse(null);
    }

    public Dish create(Dish Dish) {
        return dishRepository.save(Dish);
    }

    public List<Dish> createMany(List<Dish> dishes) {
        return dishRepository.saveAll(dishes);
    }

    public Dish update(Dish Dish) {
        return dishRepository.save(Dish);
    }

    public void delById(String id) {
        dishRepository.deleteById(id);
    }

    //lab 2 reqs
    public List<Dish> getByCategory(String category) {
        return dishRepository.findByCategory(category);
    }

    public Dish getMostExpensive() {
        return dishRepository.findAll()
                .stream()
                .max(Comparator.comparing(Dish::getPrice))
                .orElse(null);
    }

    public List<Dish> getByCaloriesLowerThan(int maxCalories) {
        return dishRepository.findAll()
                .stream()
                .filter(dish -> dish.getCalories() < maxCalories)
                .collect(Collectors.toList());
    }

}