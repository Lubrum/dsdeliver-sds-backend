package com.devsuperior.dsdeliver.dto;

import com.devsuperior.dsdeliver.entities.Product;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ProductDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Double price;
    private String description;
    private String imageUri;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, Double price, String description, String imageUri) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUri = imageUri;
    }

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();
        description = entity.getDescription();
        imageUri = entity.getImageUri();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(description, that.description) && Objects.equals(imageUri, that.imageUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, description, imageUri);
    }
}