package by.oneka.aston.dto.classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDTO{
    Integer id;
    String firstName;
    String lastName;
    Integer age;
    Integer grade;
    Integer teacherID;
}
