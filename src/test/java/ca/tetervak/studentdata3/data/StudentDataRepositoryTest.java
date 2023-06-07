package ca.tetervak.studentdata3.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentDataRepositoryTest {


    @Autowired
    StudentDataRepository repository;
    @Test
    void findByProgramId() {
        List<Student> list = repository.findByProgramId(2);
        list.forEach(out::println);
    }

    @Test
    void findByName() {
        Student bart = repository.findByName("Bart","Simpson").orElseThrow();
        out.println(bart);
    }
}
