package com.devsuperior.dsdeliver.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_order")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @ManyToMany
    @JoinTable(name = "tb_order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private final Set<Product> products = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private Double latitude;
    private Double longitude;
    private Instant moment;
    private OrderStatus status;

    public Order() {
    }

    public Order(Long id, String adress, Double latitude, Double longitude, Instant moment, OrderStatus status) {
        super();
        this.id = id;
        this.address = adress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.moment = moment;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTotal() {
        double sum;
        sum = products.stream().mapToDouble(Product::getPrice).sum();
        return sum;
    }

    public Set<Product> getProducts() {
        return products;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

}