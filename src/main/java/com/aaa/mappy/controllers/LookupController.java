package com.aaa.mappy.controllers;

import com.aaa.mappy.entity.Lookup;
import com.aaa.mappy.entity.Product;
import com.aaa.mappy.services.ProductSupportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class LookupController {


    /*
    getAll()
    search(string context,string properShippingName)
    save(Product)

    * */
    @Autowired
    ProductSupportService productSupportService;

    @GetMapping("/products")
    public List<Lookup>  getData(){

         return this.productSupportService.getAll();
    }

    public void getIngredients(){
        this.productSupportService.getProductIngredients(null);
    }

    @GetMapping("/hazmatClassification/{psn}")
    public Product getHazmatClassification(@PathVariable(name = "psn") String psn)
    {
        Optional<Product> product = this.productSupportService.getHazmatClassification(psn);
        if(product.isPresent()){
            return product.get();
        }else {
            return null;
        }
    }
}
