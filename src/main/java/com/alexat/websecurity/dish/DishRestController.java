package com.alexat.websecurity.dish;


/*
@author   AlexAT
@project   websecurity
@class  DishRestController
@version  1.0.0
@since 18.03.2025 - 16.56
*/

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes")
@RequiredArgsConstructor
public class DishRestController {
    private final DishService dishService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPERADMIN')")
    public List<Dish> getAll() {
        return dishService.getAll();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPERADMIN')")
    public Dish showOneById(@PathVariable String id) {
        return dishService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Dish insert(@RequestBody Dish Dish) {
        return dishService.create(Dish);
    }

    @PostMapping("/many")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public List<Dish> insertMultiple(@RequestBody List<Dish> dishes) {
        return dishService.createMany(dishes);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Dish edit(@RequestBody Dish Dish) {
        return dishService.update(Dish);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public void delete(@PathVariable String id) {
        dishService.delById(id);
    }

    @DeleteMapping("/clear-all")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public void clearAllDishes() {
        dishService.deleteAll();
    }

    // lab2. endpoints for req
    @GetMapping("/category/{category}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPERADMIN')")
    public List<Dish> getDishByCategory(@PathVariable String category) {
        return dishService.getByCategory(category);
    }

    @GetMapping("/most-expensive")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Dish getMostExpensiveMeal() {
        return dishService.getMostExpensive();
    }

    @GetMapping("/low-calorie/{maxCalories}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public List<Dish> getLowCalorieMeals(@PathVariable int maxCalories) {
        return dishService.getByCaloriesLowerThan(maxCalories);
    }
}
