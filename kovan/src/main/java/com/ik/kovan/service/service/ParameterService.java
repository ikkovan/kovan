package com.ik.kovan.service.service;

import com.ik.kovan.model.Parameter;
import com.ik.kovan.model.Payroll;

import java.util.HashMap;
import java.util.List;

public interface ParameterService {

    public List<Parameter> setParams(HashMap<String, String> params);

    public List<Parameter> saveAll(List<Parameter> parameters);
}
