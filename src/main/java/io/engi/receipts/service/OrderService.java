package io.engi.receipts.service;

import io.engi.receipts.exception.ResourceNotFoundException;
import io.engi.receipts.persistence.model.Entry;
import io.engi.receipts.persistence.model.Order;
import io.engi.receipts.persistence.model.OrderObject;
import io.engi.receipts.persistence.model.Product;
import io.engi.receipts.persistence.repository.EntryRepository;
import io.engi.receipts.persistence.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private ProductService productService;

    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public Order getById(long id) throws ResourceNotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
    }

    @Transactional
    public Order save(OrderObject order) {
        Order dbOrder = new Order();
        dbOrder.setDate(order.getDate());
        Order insertedOrder = orderRepository.save(dbOrder);
        List<Entry> dbEntries = new ArrayList<>();
        for (OrderObject.EntryObject entry : order.getEntries()) {
            try {
                Product product = productService.getProduct(entry.getProduct());
                Entry dbEntry = new Entry(
                        insertedOrder.getId(), product, entry.getAmount());
                dbEntries.add(dbEntry);
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        }
        List<Entry> insertedEntries = (List<Entry>) entryRepository.saveAll(dbEntries);
        insertedOrder.setEntries(insertedEntries);
        return insertedOrder;
    }
}
