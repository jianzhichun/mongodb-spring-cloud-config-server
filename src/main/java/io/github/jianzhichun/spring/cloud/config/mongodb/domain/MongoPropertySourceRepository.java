package io.github.jianzhichun.spring.cloud.config.mongodb.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPropertySourceRepository extends MongoRepository<MongoPropertySource, String> {

	MongoPropertySource findById(String id);

	List<MongoPropertySource> deleteById(String id);

	List<MongoPropertySource> findByAppAndLabelAndProfileIn(String app, String label, String... profiles);

}
