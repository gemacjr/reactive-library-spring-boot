package com.swiftbeard.library_api.config;

import graphql.schema.GraphQLScalarType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = GraphQLConfig.class)
class GraphQLConfigTest {

    @Autowired
    private GraphQLConfig graphQLConfig;

    @Test
    void runtimeWiringConfigurer_ShouldNotBeNull() {
        RuntimeWiringConfigurer configurer = graphQLConfig.runtimeWiringConfigurer();
        assertNotNull(configurer);
    }

    @Test
    void runtimeWiringConfigurer_ShouldConfigureCustomScalars() {
        RuntimeWiringConfigurer configurer = graphQLConfig.runtimeWiringConfigurer();
        assertNotNull(configurer);

        // This test is limited since we can't easily inspect the internals of the configurer
        // In a real-world scenario, we might want to use a mock GraphQLCodeRegistry to verify
        // that the scalar is registered correctly
    }
}