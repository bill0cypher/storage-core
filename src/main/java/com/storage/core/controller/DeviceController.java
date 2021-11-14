package com.storage.core.controller;

import com.storage.core.dto.DeviceDTO;
import com.storage.core.model.Device;
import com.storage.core.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/devices")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping(path = "/new")
    public ResponseEntity<Device> registerDevice(@Valid @RequestBody Device device) {
        log.info("REST request to create new device.");
        return ResponseEntity.ok(deviceService.save(device));
    }

    @PostMapping(path = "/")
    public ResponseEntity<Device> updateDevice(@Valid @RequestBody DeviceDTO device) {
        log.info("REST request to update device.");
        return ResponseEntity.ok(deviceService.update(device));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteDevice(@PathVariable("id") String serialNumber ) {
        log.info("REST request to remove device by SN: {}.", serialNumber);
        return ResponseEntity.ok(deviceService.delete(serialNumber));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Device>> findAllDevices() {
        log.info("REST request to receive all devices.");
        return ResponseEntity.ok(deviceService.findAll());
    }

    @GetMapping(path = "/by/{id}")
    public ResponseEntity<Device> findDevice(@PathVariable("id") String serialNumber) {
        log.info("REST request to receive device by id {}", serialNumber);
        return ResponseEntity.ok(deviceService.findBySerialNumber(serialNumber));
    }

    @GetMapping(path = "/by/{model}")
    public ResponseEntity<List<Device>> findDevicesByModel(@PathVariable("model") String model) {
        log.info("REST request to receive devices by model {}", model);
        return ResponseEntity.ok(deviceService.findAllByModel(model));
    }
}
