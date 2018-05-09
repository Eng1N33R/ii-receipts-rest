package io.engi.receipts.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name="product")
@RequiredArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type="pg-uuid")
    private UUID id;

    @NonNull
    private String name;

    @Column(name = "price", precision = 10, scale = 2)
    @NonNull
    private BigDecimal price;
}
