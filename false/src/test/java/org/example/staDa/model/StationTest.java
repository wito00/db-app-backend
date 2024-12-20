/*
 * StaDa - Station Data
 * An API providing master data for german railway stations managed by DB InfraGo AG.  ## License The usage of the data is subject to the Creative Commons Attribution 4.0 International (CC BY 4.0) license.\"
 *
 * The version of the OpenAPI document: 2.9.1
 * Contact: StaDa.API@deutschebahn.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.example.staDa.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.example.staDa.model.Address;
import org.example.staDa.model.Aufgabentraeger;
import org.example.staDa.model.EVANumber;
import org.example.staDa.model.MobilityServiceStaff;
import org.example.staDa.model.Partial;
import org.example.staDa.model.RegionalBereich;
import org.example.staDa.model.RiL100Identifier;
import org.example.staDa.model.SZentrale;
import org.example.staDa.model.Schedule;
import org.example.staDa.model.StationManagement;
import org.example.staDa.model.TimetableOffice;
import org.example.staDa.model.WirelessLan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Model tests for Station
 */
class StationTest {
    private final Station model = new Station();

    /**
     * Model tests for Station
     */
    @Test
    void testStation() {
        // TODO: test Station
    }

    /**
     * Test the property 'dbinformation'
     */
    @Test
    void dbinformationTest() {
        // TODO: test dbinformation
    }

    /**
     * Test the property 'aufgabentraeger'
     */
    @Test
    void aufgabentraegerTest() {
        // TODO: test aufgabentraeger
    }

    /**
     * Test the property 'category'
     */
    @Test
    void categoryTest() {
        // TODO: test category
    }

    /**
     * Test the property 'evaNumbers'
     */
    @Test
    void evaNumbersTest() {
        // TODO: test evaNumbers
    }

    /**
     * Test the property 'federalState'
     */
    @Test
    void federalStateTest() {
        // TODO: test federalState
    }

    /**
     * Test the property 'hasBicycleParking'
     */
    @Test
    void hasBicycleParkingTest() {
        // TODO: test hasBicycleParking
    }

    /**
     * Test the property 'hasCarRental'
     */
    @Test
    void hasCarRentalTest() {
        // TODO: test hasCarRental
    }

    /**
     * Test the property 'hasDBLounge'
     */
    @Test
    void hasDBLoungeTest() {
        // TODO: test hasDBLounge
    }

    /**
     * Test the property 'hasLocalPublicTransport'
     */
    @Test
    void hasLocalPublicTransportTest() {
        // TODO: test hasLocalPublicTransport
    }

    /**
     * Test the property 'hasLockerSystem'
     */
    @Test
    void hasLockerSystemTest() {
        // TODO: test hasLockerSystem
    }

    /**
     * Test the property 'hasLostAndFound'
     */
    @Test
    void hasLostAndFoundTest() {
        // TODO: test hasLostAndFound
    }

    /**
     * Test the property 'hasMobilityService'
     */
    @Test
    void hasMobilityServiceTest() {
        // TODO: test hasMobilityService
    }

    /**
     * Test the property 'hasParking'
     */
    @Test
    void hasParkingTest() {
        // TODO: test hasParking
    }

    /**
     * Test the property 'hasPublicFacilities'
     */
    @Test
    void hasPublicFacilitiesTest() {
        // TODO: test hasPublicFacilities
    }

    /**
     * Test the property 'hasRailwayMission'
     */
    @Test
    void hasRailwayMissionTest() {
        // TODO: test hasRailwayMission
    }

    /**
     * Test the property 'hasSteplessAccess'
     */
    @Test
    void hasSteplessAccessTest() {
        // TODO: test hasSteplessAccess
    }

    /**
     * Test the property 'hasTaxiRank'
     */
    @Test
    void hasTaxiRankTest() {
        // TODO: test hasTaxiRank
    }

    /**
     * Test the property 'hasTravelCenter'
     */
    @Test
    void hasTravelCenterTest() {
        // TODO: test hasTravelCenter
    }

    /**
     * Test the property 'hasTravelNecessities'
     */
    @Test
    void hasTravelNecessitiesTest() {
        // TODO: test hasTravelNecessities
    }

    /**
     * Test the property 'hasWiFi'
     */
    @Test
    void hasWiFiTest() {
        // TODO: test hasWiFi
    }

    /**
     * Test the property 'ifopt'
     */
    @Test
    void ifoptTest() {
        // TODO: test ifopt
    }

    /**
     * Test the property 'localServiceStaff'
     */
    @Test
    void localServiceStaffTest() {
        // TODO: test localServiceStaff
    }

    /**
     * Test the property 'mailingAddress'
     */
    @Test
    void mailingAddressTest() {
        // TODO: test mailingAddress
    }

    /**
     * Test the property 'mobilityServiceStaff'
     */
    @Test
    void mobilityServiceStaffTest() {
        // TODO: test mobilityServiceStaff
    }

    /**
     * Test the property 'name'
     */
    @Test
    void nameTest() {
        // TODO: test name
    }

    /**
     * Test the property 'number'
     */
    @Test
    void numberTest() {
        // TODO: test number
    }

    /**
     * Test the property 'priceCategory'
     */
    @Test
    void priceCategoryTest() {
        // TODO: test priceCategory
    }

    /**
     * Test the property 'regionalbereich'
     */
    @Test
    void regionalbereichTest() {
        // TODO: test regionalbereich
    }

    /**
     * Test the property 'ril100Identifiers'
     */
    @Test
    void ril100IdentifiersTest() {
        // TODO: test ril100Identifiers
    }

    /**
     * Test the property 'stationManagement'
     */
    @Test
    void stationManagementTest() {
        // TODO: test stationManagement
    }

    /**
     * Test the property 'szentrale'
     */
    @Test
    void szentraleTest() {
        // TODO: test szentrale
    }

    /**
     * Test the property 'timeTableOffice'
     */
    @Test
    void timeTableOfficeTest() {
        // TODO: test timeTableOffice
    }

    /**
     * Test the property 'wirelessLan'
     */
    @Test
    void wirelessLanTest() {
        // TODO: test wirelessLan
    }

}
