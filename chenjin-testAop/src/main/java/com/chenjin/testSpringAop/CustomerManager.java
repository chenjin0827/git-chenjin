package com.chenjin.testSpringAop;

public interface CustomerManager {
    void addCustomer(String name, String password);

    void deleteCustomer(String name);

    String getCustomerById(int id);

    void updateCustomer(int id, String name, String password);

}

