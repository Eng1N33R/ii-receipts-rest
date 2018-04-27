package io.engi.receipts.util;

import com.itextpdf.kernel.pdf.PdfWriter;
import io.engi.receipts.persistence.model.Order;
import io.engi.receipts.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Setter
public class PdfOrderFactory {
    final private ProductRepository productRepository;
    private Order order;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createPdf() throws IOException {
        String path = String.format("./static/receipts/%d.pdf", order.getId());
        File file = new File(path);
        file.getParentFile().mkdirs();
        PdfWriter writer = new PdfWriter(path);
        PdfAssembler assembler = new PdfAssembler(writer, productRepository);
        assembler.assemble(order);
    }
}
