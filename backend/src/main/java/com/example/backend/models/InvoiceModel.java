package com.example.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoice")
@Data
public class InvoiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    private UUID id_invoice;

    @ManyToOne
    @JoinColumn(name = "pembeli_id", nullable = false)
    private PembeliModel pembeli;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<InvoiceDetailModel> invoiceDetails = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false, name = "tanggal_pembelian")
    private Date tanggalPembelian;

    private Double totalHarga = 0.0;

    private boolean paid = false;

    public void calculateTotalHarga() {
        totalHarga = invoiceDetails.stream()
                .mapToDouble(detail -> detail.getHarga() * detail.getJumlahItem())
                .sum();
    }
}
