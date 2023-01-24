package com.store.electronic.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    private String productId;

    @Column(name = "product_name", length = 20, nullable = false)
    private String productName;

    @Column(name = "product_description", length = 1000, nullable = false)
    private String productDescription;

    @Column(name = "product_price", nullable = false)
    private double productPrice;

    @Column(name = "product_discounted_price", nullable = false)
    private double discountedPrice;

    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;

    @Column(name = "product_addedDate", nullable = false)
    private Date addedDate;

    @Column(name = "product_isActive", nullable = false)
    private boolean active;

    @Column(name = "product_stock", nullable = false)
    private boolean stock;

    @Column(name = "product_image")
    private String productImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}
