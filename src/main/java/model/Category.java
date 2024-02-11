package model;

import base.model.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString

public class Category extends BaseEntity<Integer> {

    String categoryName;

    public Category(Integer categoryId, String categoryName) {
        super(categoryId);
        this.categoryName = categoryName;
    }
}
