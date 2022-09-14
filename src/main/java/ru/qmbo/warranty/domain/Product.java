package ru.qmbo.warranty.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Заполните название")
    @Size(max = 255, message = "Название продукта слишком длинное (больше чем 255 символов)")
    private String name;
    @Column(unique = true)
    @NotBlank(message = "Заполните название модели")
    @Size(max = 10, message = "Название модели слишком длинное (больше чем 10 символов)")
    private String modelName;
    @Column(unique = true)
    @NotBlank(message = "Заполните аббревиатуру")
    @Size(max = 4, message = "Аббревиатура слишком длинная (больше чем 4 символов)")
    private String abbreviature;
}
