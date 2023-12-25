package com.luv2code.springboot.cruddemo.rest;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController
{
    private EmployeeService employeeService;

    //fast injection employee dao
    @Autowired
    public EmployeeRestController(EmployeeService theemployeeService){
     //  this.employeeService = (EmployeeService) theemployeeService;
        this.employeeService = theemployeeService;
    }

    //expose the /employee and return a list of employee
    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }
    //add mapping for GET /employee/{employee id}
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId)
    {
        Employee theEmployee = employeeService.findById(employeeId);
        if(theEmployee == null){
            throw new RuntimeException("Employee id not found - " + employeeId);
        }
        return theEmployee;
    }
    //add mapping for POST /employee - add new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee)
    {
        //also just incase they pass an id in Json .. set id to 0
        //this is a force save of new item... instead of update

        theEmployee.setId(0);
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    //add mapping for PUT /employees - Update existing employee
    @PutMapping("/employees")
    public Employee UpdateEmployee(@RequestBody Employee theEmployee)
    {
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    //add mapping for DELETE /employees/{employeeId} - delete employee
    @DeleteMapping("/employees/{employeeId}")
    public  String deleteEmployee(@PathVariable int employeeId)
    {
        Employee tempEmployee = employeeService.findById(employeeId);

        if(tempEmployee == null){
                throw  new RuntimeException(("Employee id not found - "+ employeeId));

            }
            employeeService.deleteById(employeeId);
        return "Deleted employee id - "+employeeId;

    }

}
