package pojo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ShaoJiale
 * Date: 2020/2/20
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
public class Student {
    private String name;
    private String stuNo;
    private String[] hobbies;
    private School[] schools;
}
