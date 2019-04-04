package com.shaon.springBootCrudRest.controller;


import com.shaon.springBootCrudRest.exception.ResourceNotFoundException;
import com.shaon.springBootCrudRest.model.Employee;
import com.shaon.springBootCrudRest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/employees")
    public List<Employee> getsAllEmployee(){
        return employeeService.getsAllEmployee();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {
        Employee employee = employeeService.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id: "+employeeId));
        return ResponseEntity.ok().body(employee);

    }



    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.save(employee);

    }



    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> upDateEmployee(@PathVariable(value = "id") Long employeeId, @Valid @RequestBody Employee employeedetail) throws ResourceNotFoundException {
        Employee employee = employeeService.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id: "+employeeId));

        employee.setEmailId(employeedetail.getEmailId());
        employee.setFirstName(employeedetail.getFirstName());
        employee.setLasttName(employeedetail.getLasttName());
        final Employee modifiedEmployee = employeeService.save(employee);

        return ResponseEntity.ok(modifiedEmployee);
    }


    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        Employee employee = employeeService.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employeeService.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }



}
