package org.example.domain.service;

import jakarta.inject.Inject;
import org.bson.Document;
import org.example.dao.repositories.ShopDao;

import java.util.List;

public class ShopService {
    private final ShopDao shopDao;
    @Inject
    public ShopService(ShopDao shopDao) {
        this.shopDao = shopDao;
    }

    public List<Document> getAverageRatingPerShop() {
        return shopDao.getAverageRatingPerShop();
    }
}
