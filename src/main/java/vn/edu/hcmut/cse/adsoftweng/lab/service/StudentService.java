package vn.edu.hcmut.cse.adsoftweng.lab.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import vn.edu.hcmut.cse.adsoftweng.lab.repository.StudentRepository;
import vn.edu.hcmut.cse.adsoftweng.lab.entity.Student;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;
    
    public List<Student> getAll() {
        return repository.findAll();
    }

    public Student getById(String id) {
        return repository.findById(id).orElse(null);
    }
    public List<Student> searchByName(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword);
    }
    public void save(Student student) {
        repository.save(student); 
    }
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}