package com.swiftbeard.library_api.model;

public record BookUpdateInput(Long id, String title, String author, String isbn, Integer publishYear, String genre, Boolean available) {}
