package base.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class BaseEntity <ID extends Serializable>{
    private ID id;


}
