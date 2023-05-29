package ca.tetervak.studentdata3.data;


import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDataRepository extends JpaRepository<Student, Integer> {
}
