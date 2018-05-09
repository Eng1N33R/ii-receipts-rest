package io.engi.receipts.persistence;

import io.engi.receipts.persistence.model.Product;
import io.engi.receipts.persistence.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DummyDataGenerator implements ApplicationRunner {
    private final ProductRepository productRepository;

    private static final Logger log = LoggerFactory.getLogger(
            DummyDataGenerator.class);

    @Autowired
    public DummyDataGenerator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //FIXME: Way too slow (around three minutes!)
    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (productRepository.count() == 0) {
            log.info("Beginning to input dummy data");
            List<Product> products = new ArrayList<>();
            for (int i = 0; i < 100000; i++) {
                products.add(new Product("Product" + i,
                        BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 10000))));
                if (i > 0 && i % 1000 == 0 || i == 99999) {
                    log.info("Saving batch " + (i/1000));
                    productRepository.saveAll(products);
                    products.clear();
                }
            }
        }
    }
}
