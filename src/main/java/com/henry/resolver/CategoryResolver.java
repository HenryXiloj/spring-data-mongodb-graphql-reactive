package com.henry.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.henry.model.Category;
import com.henry.model.Sequence;
import com.henry.repository.CategoryRepository;
import com.henry.repository.SequenceRepository;
import com.henry.service.CategoryService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CategoryResolver implements CategoryService, GraphQLQueryResolver, GraphQLMutationResolver {

    private final SequenceRepository sequenceRepository;
    private final CategoryRepository categoryRepository;

    public CategoryResolver(SequenceRepository sequenceRepository, CategoryRepository categoryRepository) {
        this.sequenceRepository = sequenceRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Flux<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Mono<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Mono<Category> createCategory(Category category) {
        return getNextSequenceId().map(id -> {
            category.setId(id);
            return category;
        }).flatMap(categoryRepository::save);
    }

    @Override
    public Mono<Category> updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .flatMap(category -> {
                    category.setTitle(updatedCategory.getTitle());
                    category.setPosts(updatedCategory.getPosts());
                    return categoryRepository.save(category);
                });
    }

    @Override
    public Mono<Void> deleteCategory(Long id) {
        return categoryRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAllCategories() {
        return categoryRepository.deleteAll();
    }

    private Mono<Long> getNextSequenceId() {
        return sequenceRepository.findById("categoryId")
                .map(sequence -> {
                    long nextValue = sequence.getValue() + 1;
                    sequence.setValue(nextValue);
                    return sequence;
                })
                .defaultIfEmpty(new Sequence("categoryId", 1))
                .flatMap(sequenceRepository::save)
                .map(Sequence::getValue);
    }

}
