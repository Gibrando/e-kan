package com.example.backend.services;


import com.example.backend.models.InvoiceDetailModel;
import com.example.backend.models.InvoiceModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Service
public class MidtransService {
    @Value("${midtrans.snap.url}")
    private String MIDTRANS_SNAP_URL;

    @Value("${midtrans.server.key}")
    private String SERVER_KEY;

    public String createTransaction(InvoiceModel nota) {
        RestTemplate restTemplate = new RestTemplate();

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        // Authorization header
        String authString = Base64.getEncoder().encodeToString((SERVER_KEY + ":").getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + authString);

        // Transaction details
        Map<String, Object> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", nota.getId_invoice().toString());
        transactionDetails.put("gross_amount", nota.getTotalHarga().intValue());

        //item_details
        Map<String, Object> itemDetails = new HashMap<>();
        for ( InvoiceDetailModel object  : nota.getInvoiceDetails()) {
            itemDetails.put("id" , object.getItem().getId_item());
            itemDetails.put("price" , object.getItem().getHarga());
            itemDetails.put("quantity" , object.getJumlahItem());
            itemDetails.put("name" , object.getItem().getNama());
            itemDetails.put("category" , object.getItem().getJenis_bibit());
            itemDetails.put("merchant_name" , object.getItem().getPenjual().getNama());
        }

        //customer details
        Map<String, Object> customerDetails = new HashMap<>();
        customerDetails.put("first_name" , nota.getPembeli().getNama());
        customerDetails.put("email" , nota.getPembeli().getEmail());
        customerDetails.put("phone" , nota.getPembeli().getNo_telp());

        // Request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("transaction_details", transactionDetails);
        requestBody.put("item_details", itemDetails);
        requestBody.put("customer_details", customerDetails);

        // HTTP Entity
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // Make API call
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(MIDTRANS_SNAP_URL, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Midtrans transaction creation failed", e);
        }
    }
}
