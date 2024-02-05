package com.fdmgroup.productCartApi.repository;

import org.springframework.data.repository.CrudRepository;

import com.fdmgroup.productCartApi.model.Product;


public interface ProductRepository extends CrudRepository<Product, Long>{

}
