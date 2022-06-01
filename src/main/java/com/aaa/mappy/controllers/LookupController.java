package com.aaa.mappy.controllers;

import com.aaa.mappy.entity.Lookup;
import com.aaa.mappy.entity.Product;
import com.aaa.mappy.entity.PureIngredient;
import com.aaa.mappy.services.ProductSupportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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
    public Iterable<Product> getHazmatClassification(@PathVariable(name = "psn") String psn)
    {
        String [] psns = psn.split(",");

        ArrayList<String> psnList = new ArrayList<>();
        for (String psnString: psns) {
            psnList.add(psnString.trim());
        }

        Iterable<Product> product = this.productSupportService.getHazmatClassification(psnList);
        if(product.iterator().hasNext()){
            return product;
        }else {
            return null;
        }
    }

    @GetMapping("/pure-ingredients/{psn}")
    public Iterable<PureIngredient> getPureIngredients(@PathVariable(name = "psn") String psn)
    {
        String [] psns = psn.split(",");

        ArrayList<String> psnList = new ArrayList<>();
        ArrayList<String> casList = new ArrayList<>();
        Pattern pattern = Pattern.compile("[0-9-]+");
        for (String searchString: psns) {

            if(pattern.matcher(searchString).matches()){
                casList.add(searchString.trim());
            } else {
                psnList.add(searchString.trim());
            }
        }

        ArrayList<PureIngredient> resultList = new ArrayList<>();
        Iterable<PureIngredient> psnResultList = this.productSupportService.getProductIngredientsByPSN(psnList);
        List<PureIngredient> allByCAS = this.productSupportService.getAllByCAS(casList);
        psnResultList.forEach(resultList::add);
        allByCAS.forEach(resultList::add);

        if(!resultList.isEmpty()){
            return resultList;
        }else {
            return null;
        }
    }
}
