package com.mtu.lab5;

import com.mtu.lab5.services.HouseholdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureGraphQlTester
public class GraphQLTests {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private HouseholdService householdService;

    @Test
    public void testDeleteHousehold() {
        this.graphQlTester.documentName("deleteHouseholdByEircode")
                .variable("eircode", "S23EF45")
                .execute();
        assertTrue(householdService.findByEircode("S23EF45").isEmpty());
    }
}