package com.soagrowers.pollquery.repository;


import com.soagrowers.pollquery.domain.Poll;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by ben on 07/10/15.
 */
@NoRepositoryBean
public interface ReadOnlyPagingAndSortingRepository extends PagingAndSortingRepository<Poll, String> {

    @Override
    @SuppressWarnings("unchecked")
    @RestResource(exported = false)//true means the capability will be offered
    Poll save(Poll entity);

    @Override
    @RestResource(exported = false)//false restricts the capability
    void delete(String aLong);

    @Override
    @RestResource(exported = false)
    void delete(Poll entity);
}
