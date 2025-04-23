package com.swiftbeard.library_api.config;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
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
                        .coercing(new Coercing<String, String>() {
                            @Override
                            public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                                if (dataFetcherResult instanceof String) {
                                    return (String) dataFetcherResult;
                                }
                                throw new CoercingSerializeException("Expected a String");
                            }

                            @Override
                            public String parseValue(Object input) throws CoercingParseValueException {
                                if (input instanceof String) {
                                    return (String) input;
                                }
                                throw new CoercingParseValueException("Expected a String");
                            }

                            @Override
                            public String parseLiteral(Object input) throws CoercingParseLiteralException {
                                if (input instanceof StringValue) {
                                    return ((StringValue) input).getValue();
                                }
                                throw new CoercingParseLiteralException("Expected a StringValue");
                            }
                        })
                        .build());
    }
}

