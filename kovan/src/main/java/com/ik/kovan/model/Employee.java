package com.ik.kovan.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * @author serkantan
 */

@Data // Generate getter/setters for all fields.
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue
    private int id;
    private int identityNumber;
    private String firstName;
    private String lastName;
    private boolean isMarried;

}
