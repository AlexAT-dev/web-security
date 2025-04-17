package com.alexat.websecurity;


/*
@author   AlexAT
@project   websecurity
@class  AccessTests
@version  1.0.0
@since 17.04.2025 - 15.49
*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class AccessTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final String baseUrl = "/api/v1/dishes";

    @BeforeEach
    void beforeAll() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    // ---------- GET all ----------
    @Test
    @WithAnonymousUser
    void whenGetAllAnonymous_thenStatusUnauthorized() throws Exception {
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void whenGetAllUser_thenStatusOk() throws Exception {
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk());
    }

    // ---------- GET by ID ----------
    @Test
    @WithMockUser(roles = "USER")
    void whenGetByIdUser_thenStatusOk() throws Exception {
        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void whenGetByIdAnonymous_thenStatusUnauthorized() throws Exception {
        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(status().isUnauthorized());
    }

    // ---------- POST one ----------
    @Test
    @WithMockUser(roles = "USER")
    void whenPostDishUser_thenStatusForbidden() throws Exception {
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "newFood2",
                                  "description": "description-food",
                                  "calories": 555,
                                  "price": 10.5,
                                  "category": "someCategory",
                                  "cuisine": "Italy",
                                  "preparationTime": 10
                                }"""))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenPostDishAdmin_thenStatusOk() throws Exception {
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "newFood2",
                                  "description": "description-food",
                                  "calories": 555,
                                  "price": 10.5,
                                  "category": "someCategory",
                                  "cuisine": "Italy",
                                  "preparationTime": 10
                                }"""))
                .andExpect(status().isOk());
    }

    // ---------- POST many ----------
    @Test
    @WithMockUser(roles = "SUPERADMIN")
    void whenPostManyDishesSuperAdmin_thenStatusOk() throws Exception {
        mockMvc.perform(post(baseUrl + "/many")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "name": "Tacos",
                                    "description": "Beef tacos with salsa",
                                    "calories": 300,
                                    "price": 8.99,
                                    "category": "Fast Food",
                                    "cuisine": "Mexican",
                                    "rating": 18
                                  },
                                  {
                                    "name": "Grilled Chicken",
                                    "description": "Grilled chicken with vegetables",
                                    "calories": 400,
                                    "price": 10.49,
                                    "category": "Main Course",
                                    "cuisine": "American",
                                    "rating": 22
                                  }
                                ]"""))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void whenPostManyDishesUser_thenStatusForbidden() throws Exception {
        mockMvc.perform(post(baseUrl + "/many")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "name": "Tacos",
                                    "description": "Beef tacos with salsa",
                                    "calories": 300,
                                    "price": 8.99,
                                    "category": "Fast Food",
                                    "cuisine": "Mexican",
                                    "rating": 18
                                  },
                                  {
                                    "name": "Grilled Chicken",
                                    "description": "Grilled chicken with vegetables",
                                    "calories": 400,
                                    "price": 10.49,
                                    "category": "Main Course",
                                    "cuisine": "American",
                                    "rating": 22
                                  }
                                ]"""))
                .andExpect(status().isForbidden());
    }

    // ---------- PUT ----------
    @Test
    @WithMockUser(roles = "ADMIN")
    void whenPutDishAdmin_thenStatusOk() throws Exception {
        mockMvc.perform(put(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "1",
                                  "name": "nameUpdated",
                                  "description": "description-new",
                                  "calories": 0,
                                  "price": 999,
                                  "category": "newCategory",
                                  "cuisine": "Ukraine",
                                  "preparationTime": 99
                                }"""))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void whenPutDishUser_thenStatusForbidden() throws Exception {
        mockMvc.perform(put(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "1",
                                  "name": "nameUpdated",
                                  "description": "description-new",
                                  "calories": 0,
                                  "price": 999,
                                  "category": "newCategory",
                                  "cuisine": "Ukraine",
                                  "preparationTime": 99
                                }"""))
                .andExpect(status().isForbidden());
    }

    // ---------- DELETE by ID ----------
    @Test
    @WithMockUser(roles = "ADMIN")
    void whenDeleteDishAdmin_thenStatusForbidden() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "SUPERADMIN")
    void whenDeleteDishSuperAdmin_thenStatusOk() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpect(status().isOk());
    }

    // ---------- DELETE all ----------
    @Test
    @WithMockUser(roles = "SUPERADMIN")
    void whenClearAllDishesSuperAdmin_thenStatusOk() throws Exception {
        mockMvc.perform(delete(baseUrl + "/clear-all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void whenClearAllDishesUser_thenStatusForbidden() throws Exception {
        mockMvc.perform(delete(baseUrl + "/clear-all"))
                .andExpect(status().isForbidden());
    }

    // ---------- GET by category ----------
    @Test
    @WithMockUser(roles = "USER")
    void whenGetDishesByCategoryUser_thenStatusOk() throws Exception {
        mockMvc.perform(get(baseUrl + "/category/Salad"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void whenGetDishesByCategoryAnonymous_thenStatusUnauthorized() throws Exception {
        mockMvc.perform(get(baseUrl + "/category/Salad"))
                .andExpect(status().isUnauthorized());
    }

    // ---------- GET most expensive ----------
    @Test
    @WithMockUser(roles = "ADMIN")
    void whenGetMostExpensiveDishAdmin_thenStatusOk() throws Exception {
        mockMvc.perform(get(baseUrl + "/most-expensive"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void whenGetMostExpensiveDishUser_thenStatusForbidden() throws Exception {
        mockMvc.perform(get(baseUrl + "/most-expensive"))
                .andExpect(status().isForbidden());
    }

    // ---------- GET low calorie ----------
    @Test
    @WithMockUser(roles = "ADMIN")
    void whenGetLowCalorieDishesAdmin_thenStatusOk() throws Exception {
        mockMvc.perform(get(baseUrl + "/low-calorie/500"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void whenGetLowCalorieDishesAnonymous_thenStatusUnauthorized() throws Exception {
        mockMvc.perform(get(baseUrl + "/low-calorie/500"))
                .andExpect(status().isUnauthorized());
    }
}
