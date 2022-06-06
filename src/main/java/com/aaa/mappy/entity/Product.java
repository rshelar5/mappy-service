package com.aaa.mappy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    private String hazmatClassification;

    private String classification;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(classification, other.classification);
	}

	@Override
	public int hashCode() {
		return Objects.hash(classification);
	}
    
    
    
    

}
