package com.aaa.mappy.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.aaa.mappy.entity.Lookup;
import com.aaa.mappy.entity.Product;
import com.aaa.mappy.entity.PureIngredient;
import com.aaa.mappy.repository.LookupRepository;
import com.aaa.mappy.repository.ProductRepository;
import com.aaa.mappy.repository.PureIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProductSupportService {

    HashMap<String,String> mapping = new HashMap<>();

    @Autowired
    LookupRepository lookupRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PureIngredientRepository pureIngredientRepository;

    public ProductSupportService(){
    
    }


    public void save(Product product) {
        this.productRepository.save(product);
    }

    public void save(PureIngredient pureIngredient) {
        this.pureIngredientRepository.save(pureIngredient);
    }


    public List<Lookup> getAll() {
        return (List<Lookup>) lookupRepository.findAll();
    }

    public void getProductIngredients(List<String> properShippingNameList) {
    }

    public Iterable<Product> getHazmatClassification(Iterable<String> psn) {
        return this.productRepository.findAllById(psn);
    }

    public Iterable<PureIngredient> getProductIngredientsByPSN(Iterable<String> psn) {
        return this.pureIngredientRepository.findAllById(psn);
    }

    public List<PureIngredient> getAllByCAS(ArrayList<String> casList) {
        List<PureIngredient> resultList = new ArrayList<>();
        for (String casID: casList) {
            PureIngredient byCASNumber = this.pureIngredientRepository.findByCASNumber(casID);
            resultList.add(byCASNumber);
        }
        return resultList;
    }
}
