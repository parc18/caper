package com.khelacademy.config;
//MongoClientURI connectionStr = new MongoClientURI("mongodb://manish2:welcome@2019@localhost:27017/caper");


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories
@Component
public class MongoDbConfig {


	    @Bean
	    public MongoDbFactory mongoDbFactory() {
	        return new SimpleMongoDbFactory(new MongoClientURI("mongodb://manish2:welcome2019@localhost:27017/caper"));
	    }

		@Bean
		public MongoTemplate mongoTemplate() {
			MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

			return mongoTemplate;

	    }
}