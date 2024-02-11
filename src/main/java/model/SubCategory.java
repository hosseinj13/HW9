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

public class SubCategory extends BaseEntity<Integer> {
     String subCategoryName;
     Category category;

  public SubCategory(int subCategoryId, String subCategoryName, Category category ){
      super(subCategoryId);
      this.subCategoryName = subCategoryName;
      this.category = category;
  }

    public SubCategory(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
}
