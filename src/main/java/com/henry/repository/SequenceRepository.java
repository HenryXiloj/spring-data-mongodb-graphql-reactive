package com.henry.repository;

import com.henry.model.Sequence;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SequenceRepository extends ReactiveCrudRepository<Sequence, String> {
}
