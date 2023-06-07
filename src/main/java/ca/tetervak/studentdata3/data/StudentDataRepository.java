package ca.tetervak.studentdata3.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentDataRepository extends JpaRepository<Student, Integer> {

    List<Student> findByProgramId(Integer id);

    @Query("SELECT s FROM Student s WHERE s.firstName=:firstName AND s.lastName=:lastName")
    Optional<Student> findByName(String firstName, String lastName);
}
