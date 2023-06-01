package ca.tetervak.studentdata3.data;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProgramDataRepositoryJdbcImplTest {

    @Autowired
    ProgramDataRepositoryJdbc repository;

    @BeforeEach
    void beforeEach() {
        out.println("----------------------------");
    }

    @AfterAll
    static void afterAll(){
        out.println("----------------------------");
    }

    @Test
    void findAll() {
        out.println("Testing findAll()");
        List<ProgramJdbc> list = repository.findAll();
        list.forEach(out::println);
        out.println("number of programs: " + list.size());
        assertTrue(list.size() > 0);
    }

    @Test
    void existsById() {
        out.println("Testing existsById()");
        out.println("existsById(1) = " + repository.existsById(1));
        out.println("existsById(10) = " + repository.existsById(10));
        assertTrue(repository.existsById(1));
        assertFalse(repository.existsById(10));
    }


    @Test
    void findById() {
        out.println("Testing findById()");
        ProgramJdbc program = repository.findById(1).orElseThrow();
        out.println("findById(1) = " + program);
        Optional<ProgramJdbc> opt = repository.findById(10);
        assertTrue(opt.isEmpty());
    }

    @Test
    void update() {
        out.println("Testing update()");
        ProgramJdbc program = repository.findById(1).orElseThrow();
        out.println("findById(1) = " + program);
        String name = "Android Development";
        program.setProgramName(name);
        repository.update(program);
        program = repository.findById(1).orElseThrow();
        out.println("findById(1) = " + program);
        assertEquals(name, program.getProgramName());
    }

    @Test
    void insert() {
        out.println("Testing insert()");
        ProgramJdbc program = new ProgramJdbc();
        String name = "iOS App Development";
        program.setProgramName(name);
        program = repository.insert(program);
        Integer id = program.getId();
        out.println("id = " + id);
        assertTrue(id > 0);
        program = repository.findById(id).orElseThrow();
        out.println("findById("+ id + ") = " + program);
        assertEquals(name, program.getProgramName());
    }
}
