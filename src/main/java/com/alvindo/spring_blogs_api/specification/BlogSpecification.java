package com.alvindo.spring_blogs_api.specification;

import com.alvindo.spring_blogs_api.dto.request.FilterBlogRequest;
import com.alvindo.spring_blogs_api.entity.Blog;
import jakarta.persistence.criteria.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BlogSpecification {
    public Specification<Blog> getBlogSpecification(FilterBlogRequest request){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (request.getTitle() != null) {
                predicateList.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), "%" + request.getTitle().toUpperCase() + "%"));
            }

            if (request.getDay() != null){
                Expression<Integer> dayExpression = criteriaBuilder.function("date_part", Integer.class, criteriaBuilder.literal("day"), root.get("createdAt"));
                predicateList.add(criteriaBuilder.equal(dayExpression, request.getDay()));
            }

            if (request.getMonth() != null) {
                Expression<Integer> monthExpression = criteriaBuilder.function("date_part", Integer.class, criteriaBuilder.literal("month"), root.get("createdAt"));
                predicateList.add(criteriaBuilder.equal(monthExpression, request.getMonth()));
            }

            if (request.getYear() != null) {
                Expression<Integer> yearExpression = criteriaBuilder.function("date_part", Integer.class, criteriaBuilder.literal("year"), root.get("createdAt"));
                predicateList.add(criteriaBuilder.equal(yearExpression, request.getYear()));
            }

            return query.where(predicateList.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
