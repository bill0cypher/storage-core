package com.storage.core;

import com.storage.core.dto.DeviceDTO;
import com.storage.core.exceptions.ErrorDetails;
import com.storage.core.model.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class DeviceEndpointsTest {

    @LocalServerPort
    private int localServerPort;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldRegisterNewDevice() {
        assertThat(restTemplate.postForObject(String.format("http://localhost:%d/devices/new", localServerPort),
                new Device(
                        "AO00011B52", "w530", "Laptop - Lenovo ThinkPad series"
                ), Device.class)).isInstanceOf(Device.class);
    }

    @Test
    public void shouldFailNewDeviceRegister() {
        ResponseEntity<ErrorDetails> res = restTemplate.exchange(String.format("http://localhost:%d/devices/new", localServerPort), HttpMethod.POST,
                new HttpEntity<>(new Device(
                        "ЩЕК0001", "Ы530", "Laptop - Lenovo ThinkPad series"
                )), ErrorDetails.class);
        assertThat(res.getBody()).isNotNull();
        assertThat(res.getBody().getStatus().value()).isEqualTo(400);
    }

    @Test
    public void shouldUpdateExistingDevice() {
        assertThat(restTemplate.postForObject(String.format("http://localhost:%d/devices/", localServerPort),
                new DeviceDTO(
                        "AO00011B52", "Laptop - Lenovo ThinkPad series | UPDATED"
                ), Device.class)).isExactlyInstanceOf(Device.class).isNotNull().satisfies(device ->
                assertThat(device.getSerialNumber()).isNotNull());
    }

    @Test
    public void shouldFailUpdateOnMissingDevice() {
        ResponseEntity<ErrorDetails> errorDetails = restTemplate.exchange(String.format("http://localhost:%d/devices/", localServerPort),
                HttpMethod.POST,
                new HttpEntity<>(new DeviceDTO(
                        "B432011B52", "Laptop - Fail Update"
                )), ErrorDetails.class);
        assertThat(errorDetails.getBody()).isNotNull().isExactlyInstanceOf(ErrorDetails.class).satisfies(error ->
                assertThat(error.getStatus().value()).isEqualTo(404));
    }

    @Test
    public void shouldSuccessFindAllDevices() {
        assertThat(restTemplate.exchange(String.format("http://localhost:%d/devices/%s", localServerPort),
                HttpMethod.GET,
                null,
                List.class).getBody()).satisfies(res -> {
            List<Device> devices = (List<Device>) res;
            assertThat(devices).isNotNull().hasSizeGreaterThanOrEqualTo(0);
        });
    }

    @Test
    public void shouldSuccessFindDeviceBySerialNumber() {
        assertThat(restTemplate.exchange(String.format("http://localhost:%d/devices/by/%s", localServerPort, "AO00011B52"),
                HttpMethod.GET,
                null,
                Device.class).getBody()).isNotNull();
    }

    @Test
    public void shouldSuccessFindDeviceByModel() {
        assertThat(restTemplate.exchange(String.format("http://localhost:%d/devices/by/%s", "w530"),
                HttpMethod.GET,
                null,
                List.class).getBody()).satisfies(res -> {
                    List<Device> devices = (List<Device>) res;
                    assertThat(devices).isNotNull().hasSizeGreaterThanOrEqualTo(0);
        });
    }

    @Test
    public void shouldDeleteDevice() {
        assertThat(restTemplate.exchange(String.format("http://localhost:%d/devices/%s", localServerPort, "AO00011B52"),
                HttpMethod.DELETE,
                null,
                Boolean.class).getBody()).isNotNull().isEqualTo(true);
    }

    @Test
    public void shouldFailDeleteOnMissingDevice() {
        assertThat(restTemplate.exchange(String.format("http://localhost:%d/devices/%s", localServerPort, "AO00011B52"),
                HttpMethod.DELETE,
                null,
                Boolean.class).getBody()).isNotNull().isEqualTo(false);
    }
}
