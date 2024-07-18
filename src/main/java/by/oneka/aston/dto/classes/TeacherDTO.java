package by.oneka.aston.dto.classes;

import lombok.*;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherDTO {
    Integer id;
    @NonNull
    String firstName;
    @NonNull
    String lastName;
    @NonNull
    Integer age;
    @NonNull
    Integer experience;
    @NonNull
    String subject;
}
