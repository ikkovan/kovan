package com.ik.kovan.service.impl;

import com.ik.kovan.model.Parameter;
import com.ik.kovan.model.Payroll;
import com.ik.kovan.repository.ParameterRepository;
import com.ik.kovan.service.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ParameterImpl implements ParameterService {

    @Autowired
    ParameterRepository parameterRepository;

    @Override
    public List<Parameter> setParams(HashMap<String, String> params, Long accountId, int payrollType) {
        List<Parameter> parameters = new ArrayList<>();
        for (String paramName : params.keySet()){
            Parameter parameter = new Parameter();
            parameter.setEmployeeId(accountId);
            parameter.setPayrollType(payrollType);
            parameter.setParameterName(paramName);
            parameter.setParameterValue(params.get(paramName));
            parameters.add(parameter);
        }
        return saveAll(parameters);

    }

    @Override
    public List<Parameter> saveAll(List<Parameter> parameters) {
        return parameterRepository.saveAll(parameters);
    }

    @Override
    public void delete(Long id, int type) {
        List<Parameter> parameters = parameterRepository.listParameter(id, type);
        parameterRepository.deleteAll(parameters);
    }
}
