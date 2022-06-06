package com.aaa.mappy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pure_ingredient_table")
@ToString
public class PureIngredient {

    @Id

    @Column(length = 500)
    private String properShippingName;

    private String CASNumber;

    @Column(length = 1000)
    private String hazmatCategory;

    private String UNID;

    private String hazardClass;

    private String packagingGroup;

    private String LEHS;

	
}
