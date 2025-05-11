package com.atahar.ecommerceapi.repositories;

import com.atahar.ecommerceapi.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
