package com.ik.kovan.repository;


import com.ik.kovan.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @author serkantan
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByFirstName(String name);

    Employee findById(double id);
    //Iterable<Employee> findAll();

    @Query(value = "select e from Employee e")
    List<Employee> listEmployees();

    Employee save(Employee employee);

    void delete(Employee employee);

}

