package ca.tetervak.studentdata3.data;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProgramDataRepositoryJdbcImpl implements ProgramDataRepositoryJdbc{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProgramDataRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert =
                new SimpleJdbcInsert(jdbcTemplate)
                        .withTableName("program")
                        .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<ProgramJdbc> findAll() {
        RowMapper<ProgramJdbc> rowMapper = new BeanPropertyRowMapper<>(ProgramJdbc.class);
        return jdbcTemplate.query("SELECT * FROM program", rowMapper);
    }

    @Override
    public Boolean existsById(Integer id) {
        String sql = "SELECT EXISTS(SELECT id FROM program WHERE id=?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    @Override
    public Optional<ProgramJdbc> findById(Integer id) {
        RowMapper<ProgramJdbc> rowMapper = new BeanPropertyRowMapper<>(ProgramJdbc.class);
        String sql = "SELECT * FROM program WHERE id=?";
        try{
            ProgramJdbc program = jdbcTemplate.queryForObject(sql, rowMapper, id);
            assert program != null;
            return Optional.of(program);
        } catch (DataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void update(ProgramJdbc program) {
        String sql = "UPDATE program SET program_name=? WHERE id=?";
        jdbcTemplate.update(sql, program.getProgramName(), program.getId());
    }

    @Override
    public ProgramJdbc insert(ProgramJdbc program) {
        Map<String, Object> params = new HashMap<>();
        params.put("program_name", program.getProgramName());
        Integer id = (Integer) simpleJdbcInsert.executeAndReturnKey(params);
        program.setId(id);
        return program;
    }
}
