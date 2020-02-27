package com.example.crud.controller;


import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.model.Employee;
import com.example.crud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "employees")
    public List<Employee> listAll() {
        return employeeRepository.findAll();
    }

    @GetMapping(value = "employees/{id}")
    public ResponseEntity<Employee> findById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nhan vien khong ton tai: "+id));
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping(value = "employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long id,@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
         Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nhan vien nay khong ton tai: "+id));
         employee.setEmail(employeeDetails.getEmail());
         employee.setFirstname(employeeDetails.getFirstname());
         employee.setLastname(employeeDetails.getLastname());
         final Employee updatedEmployee = employeeRepository.save(employee);
         return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping(value = "employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("nhan vien nay ko ton tai: "+id));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
