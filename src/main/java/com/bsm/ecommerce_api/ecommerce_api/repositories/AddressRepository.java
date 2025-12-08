package com.bsm.ecommerce_api.ecommerce_api.repositories;
import com.bsm.ecommerce_api.ecommerce_api.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}