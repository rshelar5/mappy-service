package com.aaa.mappy.repository;

import com.aaa.mappy.entity.Lookup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookupRepository  extends CrudRepository<Lookup,String> {
}
