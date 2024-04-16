package com.countries.info.repository;

import com.countries.info.model.Country;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CountryRepository extends CassandraRepository<Country, String>{
}
