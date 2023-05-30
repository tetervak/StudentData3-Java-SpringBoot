package ca.tetervak.studentdata3.data;

import java.util.List;

public interface ProgramDataRepositoryJdbc {

    List<ProgramJdbc> findAll();

    boolean existsById(Integer id);
}
