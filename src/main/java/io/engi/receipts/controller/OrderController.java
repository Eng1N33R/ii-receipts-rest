package io.engi.receipts.controller;

import io.engi.receipts.exception.PdfAssemblyException;
import io.engi.receipts.exception.ResourceNotFoundException;
import io.engi.receipts.persistence.model.Order;
import io.engi.receipts.persistence.repository.OrderRepository;
import io.engi.receipts.persistence.repository.ProductRepository;
import io.engi.receipts.util.PdfOrderFactory;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;

import static io.engi.receipts.util.ControllerUtils.logRequest;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("")
    @PreAuthorize("#oauth2.hasScope('read')")
    public List<Order> getAllOrders(HttpServletRequest req) {
        logRequest(req);
        return (List<Order>) orderRepository.findAll();
    }

    @PostMapping("")
    @PreAuthorize("#oauth2.hasScope('write')")
    public Order createOrder(@Valid @RequestBody Order order,
                             HttpServletRequest req) {
        Order updated = orderRepository.save(order);
        logRequest(req, updated.toString());
        try {
            PdfOrderFactory pdfFactory = new PdfOrderFactory(productRepository);
            pdfFactory.setOrder(updated);
            pdfFactory.createPdf();
        } catch (IOException e) {
            e.printStackTrace();
            throw new PdfAssemblyException("Generic output error");
        }
        return updated;
    }

    @GetMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('read')")
    public Order getOrder(@PathVariable(value = "id") Long orderId,
                          HttpServletRequest req) {
        logRequest(req);
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
    }

    @ResponseBody
    @GetMapping("/pdf/{id}")
    @PreAuthorize("#oauth2.hasScope('read')")
    public void getOrderReceipt(@PathVariable(value = "id") Long orderId,
                                HttpServletRequest req,
                                HttpServletResponse response) {
        logRequest(req);
        try {
            response.addHeader("Content-disposition",
                    String.format("attachment;filename=receipt_%d.pdf", orderId));
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            OutputStream out = response.getOutputStream();
            InputStream in = new FileInputStream(
                    new File(String.format("./static/receipts/%d.pdf", orderId)));
            StreamUtils.copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
            throw new PdfAssemblyException("Generic output error");
        }
    }
}
