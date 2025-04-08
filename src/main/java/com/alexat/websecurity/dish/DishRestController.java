package com.alexat.websecurity.dish;


/*
@author   AlexAT
@project   websecurity
@class  DishRestController
@version  1.0.0
@since 18.03.2025 - 16.56
*/

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes")
@RequiredArgsConstructor
public class DishRestController {
    private final DishService dishService;

    @GetMapping
    public List<Dish> getAll() {
        return dishService.getAll();
    }

    @GetMapping("/{id}")
    public Dish showOneById(@PathVariable String id) {
        return dishService.getById(id);
    }

    @PostMapping
    public Dish insert(@RequestBody Dish Dish) {
        return dishService.create(Dish);
    }

    @PostMapping("/many")
    public List<Dish> insertMultiple(@RequestBody List<Dish> dishes) {
        return dishService.createMany(dishes);
    }

    @PutMapping
    public Dish edit(@RequestBody Dish Dish) {
        return dishService.update(Dish);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        dishService.delById(id);
    }

    // lab2. endpoints for req
    @GetMapping("/category/{category}")
    public List<Dish> getDishByCategory(@PathVariable String category) {
        return dishService.getByCategory(category);
    }

    @GetMapping("/most-expensive")
    public Dish getMostExpensiveMeal() {
        return dishService.getMostExpensive();
    }

    @GetMapping("/low-calorie/{maxCalories}")
    public List<Dish> getLowCalorieMeals(@PathVariable int maxCalories) {
        return dishService.getByCaloriesLowerThan(maxCalories);
    }
}
