package com.ik.kovan.controller;

import com.ik.kovan.model.Employee;
import com.ik.kovan.model.PayrollSchema;
import com.ik.kovan.service.impl.PayrollSchemaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PayrollSchemaController {
    @Autowired
    PayrollSchemaImpl payrollSchemaService;

    @GetMapping("/getAllSchemas")
    public List<PayrollSchema> getSchemas(){
        System.out.println("This is getSchemas Controller.");
        return payrollSchemaService.listPayrollSchema();
    }

    @PostMapping("/postSchema")
    public PayrollSchema postSchema(@Valid @RequestBody PayrollSchema payrollSchema){
        System.out.println("This is postSchema Controller.");
        return payrollSchemaService.save(payrollSchema);
    }
}
