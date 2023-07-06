package ca.tetervak.studentdata3.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "student")
public class Student {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 0;

    @Column(name = "first_name")
    @NotBlank
    @Size(max = 30)
    private String firstName = "";

    @Column(name = "last_name")
    @NotBlank
    @Size(max = 30)
    private String lastName = "";

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(name = "international")
    private Boolean international = false;

    @Column(name = "program_year")
    @Min(1)
    @Max(3)
    private Integer programYear = 0;

    @Column(name = "program_coop")
    private Boolean programCoop = false;

    public Student() {
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

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Boolean getInternational() {
        return international;
    }

    public void setInternational(Boolean international) {
        this.international = international;
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", program=" + program +
                ", international=" + international +
                ", programYear=" + programYear +
                ", programCoop=" + programCoop +
                '}';
    }
}
