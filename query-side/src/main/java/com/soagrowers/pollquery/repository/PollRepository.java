package com.soagrowers.pollquery.repository;


import com.soagrowers.pollquery.domain.Poll;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by ben on 07/10/15.
 */
@SuppressWarnings("unchecked")
@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface PollRepository extends ReadOnlyPagingAndSortingRepository {
    public List<Poll> findBySaleable(@Param("saleable") boolean saleable);
}
