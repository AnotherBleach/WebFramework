package org.smart4j.chapter2.service;

import org.smart4j.chapter2.model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {


    public List<Customer> getCustomerList();
    public Customer getCustomer(Long id);
    public boolean createCustomer(Map<String,Object> fieldMap);
    public boolean updateCustomer(Long id,Map<String,Object>fieldMap);
    public boolean deleteCustomer(Long id);


}
