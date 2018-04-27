package io.engi.receipts.persistence.repository;

import io.engi.receipts.persistence.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
