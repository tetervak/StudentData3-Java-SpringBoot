package ca.tetervak.studentdata3.data;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProgramDataRepositoryJdbcImpl implements ProgramDataRepositoryJdbc{

    private final JdbcTemplate jdbcTemplate;

    public ProgramDataRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProgramJdbc> findAll() {
        RowMapper<ProgramJdbc> rowMapper = new BeanPropertyRowMapper<>(ProgramJdbc.class);
        return jdbcTemplate.query("SELECT * FROM program", rowMapper);
    }

    @Override
    public boolean existsById(Integer id) {
        String sql = "SELECT EXISTS(SELECT id FROM program WHERE id=?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }
}
