package com.auron.repository;

import com.auron.model.ShortUrl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortRepository extends CrudRepository<ShortUrl, Long> {

    ShortUrl findByHash(@Param("hash") String hash);

}
