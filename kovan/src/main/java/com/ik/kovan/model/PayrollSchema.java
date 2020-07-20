package com.ik.kovan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/***
 * @author serkantan
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollSchema {
    @Id
    private int schemaId;
    private String schemaName;

}
