package io.engi.receipts.util;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import io.engi.receipts.exception.PdfAssemblyException;
import io.engi.receipts.persistence.model.Order;
import io.engi.receipts.persistence.model.OrderEntry;
import io.engi.receipts.persistence.model.Product;
import io.engi.receipts.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
class PdfAssembler {
    final private OutputStream out;
    final private ProductRepository repository;

    public void assemble(Order order) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(out));
        Document document = new Document(pdf);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        DecimalFormat priceFormat = new DecimalFormat("#,###.00");

        Paragraph title = new Paragraph("Acme Inc.");
        title.setFont(bold);
        title.setFontSize(24f);
        document.add(title);

        Paragraph receipt = new Paragraph(String.format("Receipt #%020d", order.getId()));
        receipt.setFont(font);
        document.add(receipt);
        Paragraph date = new Paragraph(String.format("Date: %s",
                order.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        date.setFont(font);
        document.add(date);

        Table table = new Table(3);
        table.addHeaderCell(new Cell().add(new Paragraph("Product").setFont(bold)));
        table.addHeaderCell(new Cell().add(new Paragraph("Amount").setFont(bold)));
        table.addHeaderCell(new Cell().add(new Paragraph("Price").setFont(bold)));
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (OrderEntry entry : order.getOrderEntries()) {
            Product product = repository.findById(entry.getProduct())
                    .orElseThrow(() -> new PdfAssemblyException("Product lookup failed"));
            BigDecimal entryPrice = product.getPrice()
                    .multiply(BigDecimal.valueOf(entry.getAmount()));
            totalPrice = totalPrice.add(entryPrice);

            table.addCell(new Cell().add(new Paragraph(product.getName()).setFont(font)));
            table.addCell(new Cell().add(
                    new Paragraph(entry.getAmount() + " x " +
                            priceFormat.format(product.getPrice())).setFont(font)));
            table.addCell(new Cell().add(
                    new Paragraph(priceFormat.format(entryPrice))));
        }
        table.addCell(new Cell().add(new Paragraph("Total").setFont(bold)));
        table.addCell(new Cell().add(new Paragraph("")));
        table.addCell(new Cell().add(
                new Paragraph(priceFormat.format(totalPrice)).setFont(bold)));
        document.add(table);

        document.add(new Paragraph("Thank you!").setFont(font));
        document.add(new Paragraph("___________ I. Gavrikov").setFont(font));
        document.close();
    }
}
