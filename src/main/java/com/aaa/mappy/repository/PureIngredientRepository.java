package com.aaa.mappy.repository;

import com.aaa.mappy.entity.PureIngredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PureIngredientRepository extends CrudRepository<PureIngredient,String>{

    public PureIngredient findByCASNumber(String cas);
}
