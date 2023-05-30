package ca.tetervak.studentdata3.data;

import java.util.List;
import java.util.Optional;

public interface StudentDataRepositoryJdbc {

    Optional<StudentJdbc> findById(Integer id);
    List<StudentJdbc> findAll();
    void deleteById(Integer id);
    StudentJdbc insert(StudentJdbc student);
    void update(StudentJdbc student);
    void deleteAll();
}
