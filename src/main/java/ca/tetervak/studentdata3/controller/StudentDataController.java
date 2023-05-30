package ca.tetervak.studentdata3.controller;

import ca.tetervak.studentdata3.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class StudentDataController {

    private final Logger log = LoggerFactory.getLogger(StudentDataController.class);

    private final StudentDataRepositoryJdbc studentDataRepositoryJdbc;
    private final ProgramDataRepositoryJdbc programDataRepositoryJdbc;

    public StudentDataController(
            StudentDataRepositoryJdbc studentDataRepositoryJdbc,
            ProgramDataRepositoryJdbc programDataRepositoryJdbc
    ) {
        this.studentDataRepositoryJdbc = studentDataRepositoryJdbc;
        this.programDataRepositoryJdbc = programDataRepositoryJdbc;
    }

    @GetMapping(value={"/", "/index"})
    public String index(){
        log.trace("index() is called");
        return "Index";
    }

    @GetMapping("/list-students")
    public ModelAndView listStudents() {
        log.trace("listStudents() is called");
        List<StudentJdbc> students = studentDataRepositoryJdbc.findAll();
        log.debug("list size = " + students.size());
        return new ModelAndView("ListStudents", "students", students);
    }

    @GetMapping("/add-student")
    public ModelAndView addStudent(){
        log.trace("addStudent() is called");
        StudentJdbc student = new StudentJdbc();
        ModelAndView modelAndView =
                new ModelAndView("AddStudent",
                                    "student", student);
        List<ProgramJdbc> programs = programDataRepositoryJdbc.findAll();
        modelAndView.addObject("programs", programs);
        return modelAndView;
    }

    @PostMapping("/insert-student")
    public String insertStudent(
            @Validated @ModelAttribute("student") StudentJdbc student,
            BindingResult bindingResult,
            Model model
    ){
        log.trace("insertStudent() is called");
        log.debug("student = " + student);
        // checking for the input validation errors
        if(!programDataRepositoryJdbc.existsById(student.getProgram().getId())){
           bindingResult.rejectValue("program.id", "Invalid.student.program.id");
           log.trace("invalid program id");
           log.debug("program.id = " + student.getProgram().getId());
        }
        if (bindingResult.hasErrors()) {
            log.trace("input validation errors");
            model.addAttribute("student", student);
            List<ProgramJdbc> programs = programDataRepositoryJdbc.findAll();
            model.addAttribute("programs", programs);
            return "AddStudent";
        } else {
            log.trace("the user inputs are correct");
            StudentJdbc savedStudent = studentDataRepositoryJdbc.insert(student);
            log.debug("id = " + savedStudent.getId());
            return"redirect:/confirm-insert/" + savedStudent.getId();
        }
    }

    @GetMapping("/confirm-insert/{id}")
    public String confirmInsert(@PathVariable String id, Model model){
        log.trace("confirmInsert() is called");
        log.debug("id = " + id);
        try {
            log.trace("looking for the data in the database");
            StudentJdbc student =
                    studentDataRepositoryJdbc.findById(Integer.parseInt(id)).orElseThrow();
            log.debug("student = " + student);
            log.trace("showing the data in the confirmation page");
            model.addAttribute("student", student);
            return "ConfirmInsert";
        } catch (NumberFormatException e) {
            log.trace("the id is not an integer");
            return "DataNotFound";
        } catch (NoSuchElementException e){
            log.trace("no data for this id = " + id);
            return "DataNotFound";
        }
    }

    @GetMapping("/delete-all")
    public String deleteAll(){
        log.trace("deleteAll() is called");
        studentDataRepositoryJdbc.deleteAll();
        return "redirect:/list-students";
    }

    @GetMapping("/student-details/{id}")
    public String studentDetails(@PathVariable String id, Model model){
        log.trace("studentDetails() is called");
        log.debug("id = " + id);
        try {
            StudentJdbc student = studentDataRepositoryJdbc.findById(Integer.parseInt(id)).orElseThrow();
            model.addAttribute("student", student);
            return "StudentDetails"; // show the student data in the form to edit
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "DataNotFound";
        } catch (NoSuchElementException e){
            log.trace("no data for this id=" + id);
            return "DataNotFound";
        }
    }

    // a user clicks "Delete" link (in the table) to "DeleteStudent"
    @GetMapping("/delete-student")
    public String deleteStudent(@RequestParam String id, Model model) {
        log.trace("deleteStudent() is called");
        log.debug("id = " + id);
        try {
            StudentJdbc student = studentDataRepositoryJdbc.findById(Integer.parseInt(id)).orElseThrow();
            model.addAttribute("student", student);
            return "DeleteStudent"; // ask "Do you really want to remove?"
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "DataNotFound";
        } catch (NoSuchElementException e){
            log.trace("no data for this id = " + id);
            return "DataNotFound";
        }
    }

    // a user clicks "Remove Record" button in "DeleteStudent" page,
    // the form submits the data to "RemoveStudent"
    @PostMapping("/remove-student")
    public String removeStudent(@RequestParam String id) {
        log.trace("removeStudent() is called");
        log.debug("id = " + id);
        try {
            studentDataRepositoryJdbc.deleteById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "DataNotFound";
        }
        return "redirect:/list-students";
    }

    // a user clicks "Edit" link (in the table) to "EditStudent"
    @GetMapping("/edit-student")
    public String editStudent(@RequestParam String id, Model model) {
        log.trace("editStudent() is called");
        log.debug("id = " + id);
        try {
            StudentJdbc student = studentDataRepositoryJdbc.findById(Integer.parseInt(id)).orElseThrow();
            model.addAttribute("student", student);
            List<ProgramJdbc> programs = programDataRepositoryJdbc.findAll();
            model.addAttribute("programs", programs);
            return "EditStudent";
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "DataNotFound";
        } catch (NoSuchElementException e){
            log.trace("no data for this id = " + id);
            return "DataNotFound";
        }
    }

    // the form submits the data to "UpdateStudent"
    @PostMapping("/update-student")
    public String updateStudent(
            @Validated @ModelAttribute("student") StudentJdbc student,
            BindingResult bindingResult,
            Model model) {
        log.trace("updateStudent() is called");
        log.debug("student = " + student);
        // checking for the input validation errors
        if(!programDataRepositoryJdbc.existsById(student.getProgram().getId())){
            bindingResult.rejectValue("program.id", "Invalid.student.program.id");
            log.trace("invalid program id");
            log.debug("program.id = " + student.getProgram().getId());
        }
        if (bindingResult.hasErrors()) {
            log.trace("input validation errors");
            model.addAttribute("student", student);
            List<ProgramJdbc> programs = programDataRepositoryJdbc.findAll();
            model.addAttribute("programs", programs);
            return "EditStudent";
        } else {
            log.trace("the user inputs are correct");
            studentDataRepositoryJdbc.update(student);
            log.debug("id = " + student.getId());
            return "redirect:/student-details/" + student.getId();
        }
    }
}
