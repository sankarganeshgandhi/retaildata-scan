package com.sgglabs.retail;

import com.sgglabs.retail.model.SellerProductData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerProductDataRepository extends CrudRepository<SellerProductData, Long> {
}
