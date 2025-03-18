package com.alexat.websecurity.dish;


import org.springframework.data.mongodb.repository.MongoRepository;

/*
@author   AlexAT
@project   websecurity
@class  DishRepository
@version  1.0.0
@since 18.03.2025 - 16.50
*/

public interface DishRepository extends MongoRepository<Dish, String> {
}
