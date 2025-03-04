package com.eric.todolist.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public class PageableUtil {
    private PageableUtil(){}

    public static Sort convertAndFilterSort(Sort sort, Map<String, String> whitelistProperties) {
        return Sort.by(sort.stream()
                .filter(order -> whitelistProperties.containsKey(order.getProperty()))
                .map(order -> order.withProperty(whitelistProperties.get(order.getProperty())))
                .toList()
        );
    }

    public static Pageable convertAndFilterSort(Pageable pageable, Map<String, String> whitelistProperties) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), convertAndFilterSort(pageable.getSort(), whitelistProperties));
    }
}
