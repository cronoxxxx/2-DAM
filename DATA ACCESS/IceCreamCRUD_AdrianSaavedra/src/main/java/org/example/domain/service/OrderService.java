package org.example.domain.service;

import jakarta.inject.Inject;
import org.bson.Document;
import org.example.dao.repositories.OrderDao;

import java.util.List;

public class OrderService {
    private final OrderDao orderDao;
    @Inject
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Document> getTotalOrdersPerShop() {
        return orderDao.getTotalOrdersPerShop();
    }

    public List<Document> getHighValueOrders(double minAmount) {
        return orderDao.getHighValueOrders(minAmount);
    }
}
