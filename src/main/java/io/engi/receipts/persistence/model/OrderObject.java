package io.engi.receipts.persistence.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderObject {
    private long id;
    private LocalDateTime date;
    private List<EntryObject> entries;

    @Data
    public static class EntryObject {
        private UUID product;
        private int amount;
    }
}
