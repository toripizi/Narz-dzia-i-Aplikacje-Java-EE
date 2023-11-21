package com.toripizi.farmhub.category.controller;

import com.toripizi.farmhub.category.dto.CreateCategoryRequest;
import com.toripizi.farmhub.category.dto.GetCategoriesResponse;
import com.toripizi.farmhub.category.dto.GetCategoryResponse;
import com.toripizi.farmhub.category.dto.UpdateCategoryRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

    @Path("categories")
public interface CategoryController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    GetCategoriesResponse getCategories();

    @GET @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetCategoryResponse getCategory(@PathParam("id") UUID id);

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    void createCategory(CreateCategoryRequest req);

    @PUT @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateCategory(@PathParam("id") UUID id, UpdateCategoryRequest req);

    @DELETE @Path("{id}")
    void deleteCategory(@PathParam("id") UUID id);

}
