package ca.tetervak.studentdata3.data;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDataRepositoryJdbcImpl implements StudentDataRepositoryJdbc{

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StudentDataRepositoryJdbcImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<StudentJdbc> findById(Integer id) {
        String sql = "SELECT s.*, program.program_name " +
                "FROM (SELECT * FROM student WHERE student.id=?) AS s " +
                "INNER JOIN program ON s.program_id=program.id";
        try {
            StudentJdbc student =
                    jdbcTemplate.queryForObject(sql, new StudentRowMapperJdbc(), id);
            assert student != null;
            return Optional.of(student);
        } catch (DataAccessException e) {
            // the code above throws an exception if the record is not found
            return Optional.empty();
        }
    }

    @Override
    public List<StudentJdbc> findAll() {
        String sql = "SELECT student.*, program.program_name " +
                "FROM student INNER JOIN program ON student.program_id=program.id";
        return jdbcTemplate.query(sql, new StudentRowMapperJdbc());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM student WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public StudentJdbc insert(StudentJdbc student) {
        String sql = "INSERT INTO student "
                + "(first_name, last_name, program_id, program_year, program_coop, program_internship) "
                + "VALUES "
                + "(:first_name, :last_name, :program_id, :program_year, :program_coop, :program_internship)";

        MapSqlParameterSource params = getParameterSource(student);
        GeneratedKeyHolder keys = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keys);

        student.setId(keys.getKey()!=null?keys.getKey().intValue():0);
        return student;
    }

    private static MapSqlParameterSource getParameterSource(StudentJdbc student) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", student.getId());
        params.addValue("first_name", student.getFirstName().trim());
        params.addValue("last_name", student.getLastName().trim());
        params.addValue("program_id", student.getProgram().getId());
        params.addValue("program_year", student.getProgramYear());
        params.addValue("program_coop", student.getProgramCoop());
        params.addValue("program_internship", student.getProgramInternship());
        return params;
    }

    @Override
    public void update(StudentJdbc student) {
        String sql = "UPDATE student SET "
                + "first_name = :first_name, last_name = :last_name, "
                + "program_id = :program_id, program_year = :program_year, "
                + "program_coop = :program_coop, program_internship = :program_internship "
                + "WHERE id = :id";
        MapSqlParameterSource params = getParameterSource(student);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteAll() {
        String sql = "TRUNCATE TABLE student";
        jdbcTemplate.update(sql);
    }

    private static class StudentRowMapperJdbc implements RowMapper<StudentJdbc> {

        @Override
        public StudentJdbc mapRow(ResultSet rs, int rowNum) throws SQLException {

            ProgramJdbc program = new ProgramJdbc();
            program.setId(rs.getInt("program_id"));
            program.setProgramName(rs.getString("program_name"));

            StudentJdbc student = new StudentJdbc();
            student.setId(rs.getInt("id"));
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            student.setProgram(program);
            student.setProgramYear(rs.getInt("program_year"));
            student.setProgramCoop(rs.getBoolean("program_coop"));
            student.setProgramInternship(rs.getBoolean("program_internship"));

            return student;
        }
    }
}
