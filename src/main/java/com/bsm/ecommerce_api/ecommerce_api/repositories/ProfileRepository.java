package com.bsm.ecommerce_api.ecommerce_api.repositories;
import com.bsm.ecommerce_api.ecommerce_api.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}