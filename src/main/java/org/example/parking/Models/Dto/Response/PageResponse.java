package org.example.parking.Models.Dto.Response;

import java.util.List;

public class PageResponse<T> {
    private List<T> items;
    private Integer page;
    private Integer size;
    private Long totalItems;
    private Integer totalPages;
    private Boolean isLast;

    public PageResponse(List<T> items, Integer page, Integer size, Long totalItems, Integer totalPages, Boolean isLast) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.isLast = isLast;
    }

    // Getters và Setters
    public List<T> getItems() { return items; }
    public void setItems(List<T> items) { this.items = items; }
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
    public Long getTotalItems() { return totalItems; }
    public void setTotalItems(Long totalItems) { this.totalItems = totalItems; }
    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }
    public Boolean getIsLast() { return isLast; }
    public void setIsLast(Boolean last) { isLast = last; }
}