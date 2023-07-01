package com.henry.controller;

import com.henry.model.Category;
import com.henry.resolver.CategoryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CategoryController {

    private final CategoryResolver categoryResolver;

    @Autowired
    public CategoryController(CategoryResolver categoryResolver) {
        this.categoryResolver = categoryResolver;
    }

    @QueryMapping
    public Flux<Category> getAllCategories() {
        return categoryResolver.getAllCategories();
    }

    @QueryMapping
    public Mono<Category> getCategoryById(@Argument Long id) {
        return categoryResolver.getCategoryById(id);
    }

    @SchemaMapping(typeName = "Mutation", field = "createCategory")
    public Mono<Category> createCategory(@Argument(name = "input") Category category) {
       return categoryResolver.createCategory(category);
    }

    @SchemaMapping(typeName = "Mutation", field = "updateCategory")
    public Mono<Category> updateCategory(@Argument Long id, @Argument(name = "input") Category updatedCategory) {
        return categoryResolver.updateCategory(id, updatedCategory);
    }

    @SchemaMapping(typeName = "Mutation", field = "deleteCategory")
    public Mono<Void> deleteCategory(@Argument Long id) {
        return categoryResolver.deleteCategory(id);
    }

    @SchemaMapping(typeName = "Mutation", field = "deleteAllCategories")
    public Mono<Void> deleteAllCategories() {
        return categoryResolver.deleteAllCategories();
    }
}


