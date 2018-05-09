package io.engi.receipts.controller;

import io.engi.receipts.persistence.model.Product;
import io.engi.receipts.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private ProductRepository orderRepository;

    @GetMapping("/find/{id}/{limit}")
    public List<Product> findPartial(
            @PathVariable(value = "id") String partialId,
            @PathVariable(value = "limit") int limit,
            HttpServletRequest req) {
        return orderRepository.findByPartialId(partialId, limit);
    }

    @GetMapping("/find/{id}")
    public List<Product> findPartial(
            @PathVariable(value = "id") String partialId,
            HttpServletRequest req) {
        return findPartial(partialId, 10, req);
    }
}
