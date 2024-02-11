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

public class Factor extends BaseEntity<Integer> {

   Product product;

    public Factor(Integer factorId, Product product) {
        super(factorId);
        this.product = product;
    }

}
