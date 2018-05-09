package io.engi.receipts.service;

import io.engi.receipts.exception.ResourceNotFoundException;
import io.engi.receipts.persistence.model.Product;
import io.engi.receipts.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @SuppressWarnings("WeakerAccess")
    public Product getProduct(UUID id) throws ResourceNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
    }
}
