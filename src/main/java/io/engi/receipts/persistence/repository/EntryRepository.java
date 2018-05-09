package io.engi.receipts.persistence.repository;

import io.engi.receipts.persistence.model.Entry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface EntryRepository extends CrudRepository<Entry, Entry.Key> {
}
