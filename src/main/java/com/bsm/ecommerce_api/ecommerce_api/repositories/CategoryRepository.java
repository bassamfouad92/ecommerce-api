package com.bsm.ecommerce_api.ecommerce_api.repositories;
import com.bsm.ecommerce_api.ecommerce_api.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}