package org.smart4j.chapter2.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CustomerServiceImpl implements CustomerService {

    private static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Override
    public List<Customer> getCustomerList() {


        String sql = "SELECT * FROM customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);

    }

    @Override
    public Customer getCustomer(Long id) {
        return null;
    }

    @Override
    public boolean createCustomer(Map<String, Object> fieldMap) {
        return false;
    }

    @Override
    public boolean updateCustomer(Long id, Map<String, Object> fieldMap) {
        return false;
    }

    @Override
    public boolean deleteCustomer(Long id) {
        return false;
    }
}
