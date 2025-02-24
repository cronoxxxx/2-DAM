package org.example.simulacroweb.dao;

import org.example.simulacroweb.domain.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class DaoProducto {

    private Database db;

    public DaoProducto(Database db) {
        this.db = db;
    }

    public List<Product> getAll(){
        return new ArrayList<>(db.getProducts());
    }

    public Product getById(int id){
        return db.getProducts().stream().filter(product -> product.getId()==id).findFirst().orElseThrow(null);
    }

    public boolean add(Product p){
        p.setId(db.getProducts().stream().mapToInt(Product::getId).max().orElse(0)+1);
        return db.getProducts().add(p);
    }

    public boolean remove(int id){
         return db.getProducts().removeIf(product -> product.getId()==id);
    }


}
