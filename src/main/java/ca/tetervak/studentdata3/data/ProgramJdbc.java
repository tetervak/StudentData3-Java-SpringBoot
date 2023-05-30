package ca.tetervak.studentdata3.data;

public class ProgramJdbc {

    private Integer id = 0;
    private String programName = "";

    public ProgramJdbc() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", programName='" + programName + '\'' +
                '}';
    }
}
