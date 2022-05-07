package com.aaa.mappy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_table")
public class Product {

    @Id
    private String properShippingName;

    private String CASNumber;

    private String hazmatClassification;

    private String hazmatCategory;

    private String classification;

    private String UNID;

    private String PackingGroup;

    private String LEHS;

}
