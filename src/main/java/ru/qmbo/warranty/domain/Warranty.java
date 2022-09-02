package ru.qmbo.warranty.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "warranties")
public class Warranty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private String serialNumber;
}
