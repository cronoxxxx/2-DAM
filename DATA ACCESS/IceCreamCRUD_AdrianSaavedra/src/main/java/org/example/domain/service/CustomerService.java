package org.example.domain.service;

import jakarta.inject.Inject;
import org.bson.Document;
import org.example.dao.model.Customer;
import org.example.dao.repositories.CustomerDao;

import java.util.List;

public class CustomerService {
    private final CustomerDao customerDao;
    @Inject
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Document> getTopCustomersByLoyaltyPoints() {
        return customerDao.getTopCustomersByLoyaltyPoints();
    }
}
