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

    Employee findById(long id);
    //Iterable<Employee> findAll();

    @Query(value = "select e from Employee e where e.isWorking=true")
    List<Employee> listEmployees();

    @Query(value = "select e from Employee e where e.isWorking=false")
    List<Employee> listOldEmployees();


    Employee save(Employee employee);

    void delete(Employee employee);

}

