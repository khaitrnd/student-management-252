package vn.edu.hcmut.cse.adsoftweng.lab.controller; 

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.edu.hcmut.cse.adsoftweng.lab.service.StudentService;
import vn.edu.hcmut.cse.adsoftweng.lab.entity.Student;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentWebController {
    
    @Autowired
    private StudentService service;
    
    // Route: GET http://localhost:8080/students
    @GetMapping
        public String getAllStudents (@RequestParam(required = false) String keyword, Model model) {
            List<Student> students;
            if (keyword != null && !keyword.isEmpty()) {
                students = service.searchByName(keyword);
            } else {
                students = service.getAll();
            }
            
            model.addAttribute("dsSinhVien", students);
            model.addAttribute("newStudent", new Student());
            return "students";
        }
    // Mở trang Form Thêm Mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student()); // Truyền một đối tượng rỗng sang form để điền
        return "student-create";
    }

    // Nhận dữ liệu từ Form và Lưu xuống Database
    @PostMapping("/create")
    public String saveStudent(@ModelAttribute("student") Student student) {
        service.save(student);
        return "redirect:/students"; // Lưu thành công -> Quay về Trang Danh Sách
    }
    @GetMapping("/{id}")
    public String viewStudentDetail(@PathVariable String id, Model model) {
        Student student = service.getById(id);
        if (student == null) {
            return "redirect:/students"; // Nếu không tìm thấy thì quay về trang danh sách
        }
        model.addAttribute("student", student);
        return "student-detail";
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/students"; // Xóa xong quay về trang danh sách
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Student student = service.getById(id);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "student-edit";
    }
    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable String id, @ModelAttribute("student") Student student) {
        student.setId(id); // Đảm bảo ID không bị thay đổi
        service.save(student); // Hàm save() của JPA sẽ tự động hiểu là Update nếu ID đã tồn tại
        return "redirect:/students"; // Lưu xong quay về trang danh sách
    }
}