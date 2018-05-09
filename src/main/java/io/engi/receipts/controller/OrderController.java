package io.engi.receipts.controller;

import io.engi.receipts.exception.PdfAssemblyException;
import io.engi.receipts.exception.ResourceNotFoundException;
import io.engi.receipts.persistence.model.Order;
import io.engi.receipts.persistence.model.OrderObject;
import io.engi.receipts.service.OrderService;
import io.engi.receipts.util.PdfAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("")
    @ResponseBody
    public Order createOrder(@Valid @RequestBody OrderObject order) {
        return orderService.save(order);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable(value = "id") Long orderId)
            throws ResourceNotFoundException {
        return orderService.getById(orderId);
    }

    @ResponseBody
    @GetMapping(value = "/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public void getOrderReceipt(@PathVariable(value = "id") Long orderId,
                                HttpServletResponse response)
            throws PdfAssemblyException {
        try {
            response.addHeader("Content-disposition",
                    String.format("attachment;filename=receipt_%020d.pdf", orderId));
            OutputStream out = response.getOutputStream();
            PdfAssembler assembler = new PdfAssembler(out);
            assembler.assemble(orderService.getById(orderId));
        } catch (IOException e) {
            throw new PdfAssemblyException("Error writing response");
        } catch (ResourceNotFoundException e) {
            throw new PdfAssemblyException(
                    String.format("Error getting order %d", orderId));
        }
    }
}
