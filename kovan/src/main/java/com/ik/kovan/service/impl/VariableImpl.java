package com.ik.kovan.service.impl;

import com.ik.kovan.logic.HibernateUtil;
import com.ik.kovan.model.Variable;
import com.ik.kovan.repository.VariableRepository;
import com.ik.kovan.service.service.VariableService;
import org.hibernate.Session;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VariableImpl implements VariableService {

    @Autowired
    HibernateUtil hibernateUtil;

    @Autowired
    VariableRepository variableRepository;

    public VariableImpl(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
        hibernateUtil.getSessionFactory();
    }

    public List<String> showTables(){
        Session session = hibernateUtil.getSessionFactory().openSession();
        Query query = session.createSQLQuery("select table_name from information_schema.tables where table_schema='public'");

        return query.list();

    }

    public List<String> showColumns(String tableName){

        Session session = hibernateUtil.getSessionFactory().openSession();
        Query query = session.createSQLQuery("select column_name from information_schema.columns where table_name = '" + tableName + "'");

        return query.list();
    }

    public String getPK(String tableName){

        Session session = hibernateUtil.getSessionFactory().openSession();
        Query query = session.createSQLQuery("SELECT c.column_name " +
                "FROM information_schema.key_column_usage AS c " +
                "LEFT JOIN information_schema.table_constraints AS t " +
                "ON t.constraint_name = c.constraint_name " +
                "WHERE t.table_name = '" + tableName + "' AND t.constraint_type = 'PRIMARY KEY'" );

        if (query.list().size() > 0)
            return String.valueOf(query.list().get(0));
        else
            return "";
    }

    public String showValue(List<String> tableAndColumn, Long id){
        Session session = hibernateUtil.getSessionFactory().openSession();
        String pk = getPK(tableAndColumn.get(0));
        System.out.println(pk);
        Query query = session.createSQLQuery("select " + tableAndColumn.get(1) + " from " + tableAndColumn.get(0) + " where " + pk + " = " + id);
        if (query.list().size() == 0) {
            System.out.println("No result!");
            return "";
        }
        else
            return String.valueOf(query.list().get(0));
    }
    public String showValueInterpreter(String tableAndColumn, Long id){
        Session session = hibernateUtil.getSessionFactory().openSession();
        List<String> tableAndColumnList = Arrays.asList(tableAndColumn.split("/"));
        String pk = getPK(tableAndColumnList.get(0));
        System.out.println(pk);
        Query query = session.createSQLQuery("select " + tableAndColumnList.get(1) + " from " + tableAndColumnList.get(0) + " where " + pk + " = " + id);
        if (query.list().size() == 0) {
            System.out.println("No result!");
            return "";
        }
        else
            return String.valueOf(query.list().get(0));
    }

    public List<String> showTablesAndColumns () {
        List<String> tableAndColumn = new ArrayList<>();
        List<String> tables = showTables();
        for (String table : tables) {
            List<String> columns = showColumns(table);
            for (String column : columns){
                tableAndColumn.add(table + "/" + column);
            }
        }
        return tableAndColumn;
    }

    /*
    public void setVarCommand(List<Variable> variables, Command command){
        for (Variable variable : variables){
            variable.setCommand(command);
        }
    }
     */

    public void  saveAll(List<Variable> variables){
        variableRepository.saveAll(variables);
    }

    @Override
    public List<Variable> listVariable() {
        return variableRepository.listVariable();
    }

    public List<List<String>> tableColumn(List<Variable> variables){
        List<List<String>> tableColumnList = new ArrayList<List<String>>();
        for (Variable variable : variables){
            List<String> foo = new ArrayList<String>();
            foo.add(variable.getLocatedTable());
            foo.add(variable.getLocatedColumn());
            tableColumnList.add(foo);
        }
        return tableColumnList;
    }
}

