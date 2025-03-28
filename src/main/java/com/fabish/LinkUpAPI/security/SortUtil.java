package com.fabish.LinkUpAPI.security;

import org.springframework.data.domain.Sort;

public class SortUtil {
    public static Sort parseSort(String sort) {
        if (sort == null || sort.trim().isEmpty()) {
            return Sort.unsorted();
        }

        String[] parts = sort.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Sort parameter must be in format 'field,direction'");
        }

        String field = parts[0].trim();
        String direction = parts[1].trim().toLowerCase();
        Sort.Direction sortDirection = direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(sortDirection, field);
    }
}
