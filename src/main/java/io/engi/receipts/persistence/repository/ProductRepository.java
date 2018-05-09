package io.engi.receipts.persistence.repository;

import io.engi.receipts.persistence.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {
    @Query(value = "select * from product where cast(id as text) like ?1% limit ?2",
            countQuery = "select count(*) from product where cast(id as text) like ?1%",
            nativeQuery = true)
    List<Product> findByPartialId(String partialId, int limit);
}
