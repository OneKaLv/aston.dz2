package by.oneka.aston.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {
    Integer id;
    String firstName;
    String lastName;
    Integer age;
    Integer grade;
    Integer teacherID;
}
