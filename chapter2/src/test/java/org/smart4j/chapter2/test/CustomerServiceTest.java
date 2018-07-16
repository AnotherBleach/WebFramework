package org.smart4j.chapter2.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;
import org.smart4j.chapter2.service.CustomerServiceImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CustomerServiceTest {

    private  final CustomerService customerService;

    public CustomerServiceTest() {
        this.customerService = new CustomerServiceImpl();
    }

    @Before
    public  void init()
    {

        System.out.println("Initializing...");


    }

    @Test
    public void getCustomerListTest() throws  Exception
    {


        List<Customer> customerList = customerService.getCustomerList();
        for(Iterator<Customer> it = customerList.iterator();it.hasNext();)
        {
            Customer customer = it.next();
            System.out.println(customer);

        }

        Assert.assertEquals(2,customerList.size());

    }

    @Test
    public  void getCustomerTest() throws  Exception
    {
            Long id = 1L;
            Customer customer = customerService.getCustomer(id);
            Assert.assertNotNull(customer);



    }

    @Test
    public void createCustomerTest() throws  Exception
    {

        Map<String,Object>fieldMap = new HashMap<>();
        fieldMap.put("name","customer100");
        fieldMap.put("telephone","188829870934");
        fieldMap.put("contact","John");
        boolean result = customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);

    }

    @Test
    public void updateCustomerTest() throws  Exception
    {

        long id = 1;
        Map<String,Object> fieldMap = new HashMap<>();
        fieldMap.put("contact","eric");
        boolean result = customerService.updateCustomer(id,fieldMap);
        Assert.assertTrue(result);


    }


    @Test
    public void deleteCustomerTest() throws  Exception
    {

        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);


    }

}
