package org.example.simulacroweb.domain.services;

import org.example.simulacroweb.dao.DaoProducto;
import org.example.simulacroweb.domain.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProducto {
    private DaoProducto daoProducto;

    public ServiceProducto(DaoProducto daoProducto) {
        this.daoProducto = daoProducto;
    }

    public boolean add(Product product){
        return daoProducto.add(product);
    }

    public boolean remove(int id){
        return daoProducto.remove(id);
    }

    public List<Product> getAll(){
        return daoProducto.getAll();
    }

    public Product getById(int id){
        return daoProducto.getById(id);
    }
}
