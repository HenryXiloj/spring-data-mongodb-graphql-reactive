package com.henry.service;

import com.henry.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {
    public Flux<Category> getAllCategories();
    public Mono<Category> getCategoryById(Long id);
    public Mono<Category> createCategory(Category category);
    public Mono<Category> updateCategory(Long id, Category updatedCategory);

    public Mono<Void> deleteCategory(Long id);

    public Mono<Void> deleteAllCategories();
}
