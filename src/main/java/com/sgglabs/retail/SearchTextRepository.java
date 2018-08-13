package com.sgglabs.retail;

import com.sgglabs.retail.model.SearchText;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchTextRepository extends CrudRepository<SearchText, Long> {
}
