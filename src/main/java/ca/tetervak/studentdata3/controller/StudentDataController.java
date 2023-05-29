package ca.tetervak.studentdata3.controller;

import ca.tetervak.studentdata3.data.Program;
import ca.tetervak.studentdata3.data.ProgramDataRepository;
import ca.tetervak.studentdata3.data.Student;
import ca.tetervak.studentdata3.data.StudentDataRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StudentDataController {

    private final Logger log = LoggerFactory.getLogger(StudentDataController.class);

    private final StudentDataRepository studentDataRepository;
    private final ProgramDataRepository programDataRepository;

    public StudentDataController(
            StudentDataRepository studentDataRepository,
            ProgramDataRepository programDataRepository
    ) {
        this.studentDataRepository = studentDataRepository;
        this.programDataRepository = programDataRepository;
    }

    @GetMapping(value={"/", "/index"})
    public String index(){
        log.trace("index() is called");
        return "Index";
    }

    @GetMapping("/list-students")
    public ModelAndView listStudents() {
        log.trace("listStudents() is called");
        List<Student> students = studentDataRepository.findAll();
        log.debug("list size = " + students.size());
        return new ModelAndView("ListStudents", "students", students);
    }

    @GetMapping("/add-student")
    public ModelAndView addStudent(){
        log.trace("addStudent() is called");
        Student student = new Student();
        ModelAndView modelAndView =
                new ModelAndView("AddStudent",
                                    "student", student);
        List<Program> programs = programDataRepository.findAll();
        modelAndView.addObject("programs", programs);
        return modelAndView;
    }

    @PostMapping("/insert-student")
    public String insertStudent(
            @Validated @ModelAttribute Student student,
            BindingResult bindingResult,
            Model model
    ){
        log.trace("insertStudent() is called");
        log.debug("student = " + student);
        // checking for the input validation errors
        if(!programDataRepository.existsById(student.getProgram().getId())){
           bindingResult.rejectValue("program.id", "Invalid.student.program.id");
           log.trace("invalid program id");
        }
        if (bindingResult.hasErrors()) {
            log.trace("input validation errors");
            model.addAttribute("student", student);
            List<Program> programs = programDataRepository.findAll();
            model.addAttribute("programs", programs);
            return "AddStudent";
        } else {
            log.trace("the user inputs are correct");
            Student savedStudent = studentDataRepository.save(student);
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
            Student student =
                    studentDataRepository.getReferenceById(Integer.parseInt(id));
            log.trace("showing the data in the confirmation page");
            model.addAttribute("student", student);
            return "ConfirmInsert";
        } catch (NumberFormatException e) {
            log.trace("the id is not an integer");
            return "DataNotFound";
        } catch (EntityNotFoundException e){
            log.trace("no data for this id=" + id);
            return "DataNotFound";
        }
    }

    @GetMapping("/delete-all")
    public String deleteAll(){
        log.trace("deleteAll() is called");
        studentDataRepository.deleteAll();
        return "redirect:/list-students";
    }

    @GetMapping("/student-details/{id}")
    public String studentDetails(@PathVariable String id, Model model){
        log.trace("studentDetails() is called");
        log.debug("id = " + id);
        try {
            Student student = studentDataRepository.getReferenceById(Integer.parseInt(id));
            model.addAttribute("student", student);
            return "StudentDetails"; // show the student data in the form to edit
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "DataNotFound";
        } catch (EntityNotFoundException e){
            log.trace("no data for this id=" + id);
            return "DataNotFound";
        }
    }

    // a user clicks "Delete" link (in the table) to "DeleteStudent"
    @GetMapping("/delete-student")
    public String deleteStudent(@RequestParam String id, Model model) {
        log.trace("deleteStudent() is called");
        try {
            Student student = studentDataRepository.getReferenceById(Integer.parseInt(id));
            model.addAttribute("student", student);
            return "DeleteStudent"; // ask "Do you really want to remove?"
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "DataNotFound";
        } catch (EntityNotFoundException e){
            log.trace("no data for this id=" + id);
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
            studentDataRepository.deleteById(Integer.parseInt(id));
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
            Student student = studentDataRepository.getReferenceById(Integer.parseInt(id));
            model.addAttribute("student", student);
            List<Program> programs = programDataRepository.findAll();
            model.addAttribute("programs", programs);
            return "EditStudent";
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "DataNotFound";
        } catch (EntityNotFoundException e){
            log.trace("no data for this id=" + id);
            return "DataNotFound";
        }
    }

    // the form submits the data to "UpdateStudent"
    @PostMapping("/update-student")
    public String updateStudent(
            @Validated @ModelAttribute Student student,
            BindingResult bindingResult,
            Model model) {
        log.trace("updateStudent() is called");
        log.debug("studentData = " + student);
        // checking for the input validation errors
        if (bindingResult.hasErrors()) {
            log.trace("input validation errors");
            model.addAttribute("student", student);
            List<Program> programs = programDataRepository.findAll();
            model.addAttribute("programs", programs);
            return "EditStudent";
        } else {
            log.trace("the user inputs are correct");
            studentDataRepository.save(student);
            log.debug("id = " + student.getId());
            return "redirect:/student-details/" + student.getId();
        }
    }
}
