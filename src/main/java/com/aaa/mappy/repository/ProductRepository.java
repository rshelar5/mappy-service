package com.aaa.mappy.repository;

import com.aaa.mappy.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product,String>{
}
