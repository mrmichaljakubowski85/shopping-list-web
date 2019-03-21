package com.tomtre.shoppinglist.web.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class ProductDto {

    private Long id;

    @NotEmpty(message = "is required")
    @Size(max = 255, message = "max 255 characters")
    private String title;

    @Size(max = 1000, message = "max 1000 characters")
    private String description;

    @Size(max = 255, message = "max 255 characters")
    private String quantity;

    @Size(max = 255, message = "max 255 characters")
    private String unit;

    private boolean marked;

    public ProductDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit='" + unit + '\'' +
                ", marked=" + marked +
                '}';
    }
}
