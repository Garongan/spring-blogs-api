package com.alvindo.spring_blogs_api.specification;

import com.alvindo.spring_blogs_api.dto.request.FilterCreatorRequest;
import com.alvindo.spring_blogs_api.entity.Creator;
import jakarta.persistence.criteria.Predicate;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CreatorSpecification {
    public Specification<Creator> getCreatorSpecification(FilterCreatorRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (request.getName() != null){
                predicateList.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + request.getName().toUpperCase() + "%"));
            }

            return query.where(predicateList.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
