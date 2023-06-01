package ca.tetervak.studentdata3.data;

import java.util.List;
import java.util.Optional;

public interface ProgramDataRepositoryJdbc {

    List<ProgramJdbc> findAll();

    Boolean existsById(Integer id);

    Optional<ProgramJdbc> findById(Integer id);
}
