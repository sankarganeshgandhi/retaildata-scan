package com.sgglabs.retail;

import com.sgglabs.retail.model.ProductSearchResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchResultRepository extends CrudRepository<ProductSearchResult, Long> {
}
