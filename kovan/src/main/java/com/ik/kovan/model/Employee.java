package com.ik.kovan.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * @author serkantan
 */

@Entity
@Table
public class Employee {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private int salaryTemplate;
    private int isMarried; // ya burası bir enum olarak da tutulabilir ama hız kaybetmemek için böyle gidiyorum.
    private int isSpouseWorking;
    private int countChildren;
    private int taxBands; // vergi dilimi

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSalaryTemplate() {
        return salaryTemplate;
    }

    public void setSalaryTemplate(int salaryTemplate) {
        this.salaryTemplate = salaryTemplate;
    }

    public int getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(int isMarried) {
        this.isMarried = isMarried;
    }

    public int getIsSpouseWorking() {
        return isSpouseWorking;
    }

    public void setIsSpouseWorking(int isSpouseWorking) {
        this.isSpouseWorking = isSpouseWorking;
    }

    public int getCountChildren() {
        return countChildren;
    }

    public void setCountChildren(int countChildren) {
        this.countChildren = countChildren;
    }

    public int getTaxBands() {
        return taxBands;
    }

    public void setTaxBands(int taxBands) {
        this.taxBands = taxBands;
    }
}
