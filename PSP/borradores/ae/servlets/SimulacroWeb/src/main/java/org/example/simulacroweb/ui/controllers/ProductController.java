package org.example.simulacroweb.ui.controllers;
import jakarta.servlet.http.HttpSession;
import org.example.simulacroweb.domain.model.Product;
import org.example.simulacroweb.domain.model.User;
import org.example.simulacroweb.domain.services.ServiceProducto;
import org.example.simulacroweb.domain.services.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ServiceProducto serviceProducto;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", serviceProducto.getAll());
        return "products";
    }

    @GetMapping("/add")
    public String addProductForm() {
        return "add-product";
    }

    @PostMapping("/add")
    public String addProductSubmit(@RequestParam String name, @RequestParam double price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        serviceProducto.add(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable int id, Model model) {
        Product product = serviceProducto.getById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "product-details";
        } else {
            return "redirect:/products";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        serviceProducto.remove(id);
        return "redirect:/products";
    }
}