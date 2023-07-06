package ca.tetervak.studentdata3.data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StudentJdbc {

    private Integer id = 0;

    @NotBlank
    @Size(max = 30)
    private String firstName = "";

    @NotBlank
    @Size(max = 30)
    private String lastName = "";

    private ProgramJdbc program;

    private Boolean international = false;

    @Min(1)
    @Max(3)
    private Integer programYear = 0;

    private Boolean programCoop = false;

    public StudentJdbc() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ProgramJdbc getProgram() {
        return program;
    }

    public void setProgram(ProgramJdbc program) {
        this.program = program;
    }

    public Integer getProgramYear() {
        return programYear;
    }

    public void setProgramYear(Integer programYear) {
        this.programYear = programYear;
    }

    public Boolean getProgramCoop() {
        return programCoop;
    }

    public void setProgramCoop(Boolean programCoop) {
        this.programCoop = programCoop;
    }

    public Boolean getInternational() {
        return international;
    }

    public void setInternational(Boolean international) {
        this.international = international;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", program=" + program +
                ", programYear=" + programYear +
                ", programCoop=" + programCoop +
                ", international=" + international +
                '}';
    }
}
