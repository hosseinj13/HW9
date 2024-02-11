package model;

import base.model.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString


public class Product extends BaseEntity<Integer> {

    String productName;
    Double productPrice;
    SubCategory subCategory;
    int productNumber;

    public Product(int productId, String productName, Double productPrice, int productNumber, SubCategory subCategory) {
        super(productId);
        this.productName = productName;
        this.productPrice = productPrice;
        this.productNumber = productNumber;
        this.subCategory = subCategory;
    }
    public Product( String productName, Double productPrice, int productNumber, SubCategory subCategory) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productNumber = productNumber;
        this.subCategory = subCategory;
    }
}
