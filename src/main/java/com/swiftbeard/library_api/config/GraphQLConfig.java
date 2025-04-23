package com.swiftbeard.library_api.config;

import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    /**
     * Configure custom scalar types and other GraphQL settings
     */
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(GraphQLScalarType.newScalar()
                        .name("ISBN")
                        .description("ISBN scalar type")
                        .coercing(new GraphQLScalarType.Builder().name("String").build().getCoercing())
                        .build());
    }
}
