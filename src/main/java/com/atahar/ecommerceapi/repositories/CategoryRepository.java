package com.atahar.ecommerceapi.repositories;

import com.atahar.ecommerceapi.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}
