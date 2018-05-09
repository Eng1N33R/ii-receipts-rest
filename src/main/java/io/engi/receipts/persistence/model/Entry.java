package io.engi.receipts.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name="entry")
@IdClass(Entry.Key.class)
@AllArgsConstructor
@NoArgsConstructor
public class Entry implements Serializable {
    @Id
    @Column(name="order_id", nullable = false)
    private long order;
    @Id
    @OneToOne
    @JoinColumn(name="product_id")
    private Product product;
    @NonNull
    @NotNull
    private int amount;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Key implements Serializable {
        private long order;
        private UUID product;
    }
}
