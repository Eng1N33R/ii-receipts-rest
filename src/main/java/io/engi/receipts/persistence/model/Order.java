package io.engi.receipts.persistence.model;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.engi.receipts.persistence.type.StringJsonType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name="orders")
@RequiredArgsConstructor
@NoArgsConstructor
@TypeDefs({@TypeDef( name= "StringJsonObject", typeClass = StringJsonType.class)})
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;

    @NonNull
    @NotNull
    @Type(type="StringJsonObject")
    @Column(name="order_entries")
    private String orderEntries;

    @Transient
    private String pdfUri;

    @SuppressWarnings("unused")
    public String getPdfUri() {
        return String.format("orders/pdf/%d", id);
    }

    public List<OrderEntry> getOrderEntries() {
        List<OrderEntry> entries = new ArrayList<>();
        try {
            JsonFactory factory = new JsonFactory();
            ObjectMapper mapper = new ObjectMapper();
            JsonParser parser = factory.createParser(this.orderEntries);
            entries = Arrays.asList(mapper.readValue(parser, OrderEntry[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
