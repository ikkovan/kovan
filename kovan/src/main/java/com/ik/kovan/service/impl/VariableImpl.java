package com.ik.kovan.service.impl;

import com.ik.kovan.logic.HibernateUtil;
import org.hibernate.Session;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VariableImpl {

    @Autowired
    HibernateUtil hibernateUtil;

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
}

