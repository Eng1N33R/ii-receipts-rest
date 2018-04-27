package io.engi.receipts.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntry implements Serializable {
    private UUID product;
    private int amount;

    @Override
    public String toString() {
        return String.format("(%s, %d)", product.toString(), amount);
    }
}
