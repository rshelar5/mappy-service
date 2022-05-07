package com.aaa.mappy.services;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.aaa.mappy.entity.Lookup;
import com.aaa.mappy.entity.Product;
import com.aaa.mappy.repository.LookupRepository;
import com.aaa.mappy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProductSupportService {

    HashMap<String,String> mapping = new HashMap<>();

    @Autowired
    LookupRepository lookupRepository;

    @Autowired
    ProductRepository productRepository;

    public ProductSupportService(){
    
    }


    public void save(Product product) {
        this.productRepository.save(product);
    }


    public List<Lookup> getAll() {
        return (List<Lookup>) lookupRepository.findAll();
    }

    public void getProductIngredients(List<String> properShippingNameList) {
    }

    public void getProductPureIngredients(List<String> properShippingNameList) {
    }
    public boolean classifyEsentialOil(List<String> properShippingNameList) {
         return  false;
    }

    public Iterable<Product> getHazmatClassification(Iterable<String> psn) {
        return this.productRepository.findAllById(psn);
    }
}
