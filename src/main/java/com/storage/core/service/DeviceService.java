package com.storage.core.service;

import com.storage.core.dto.DeviceDTO;
import com.storage.core.model.Device;
import com.storage.core.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    public Device update(DeviceDTO device) {
        Device storedDevice = deviceRepository.findById(device.getSerialNumber()).orElseThrow();
        storedDevice.setDescription(device.getDescription());
        deviceRepository.save(storedDevice);
        return storedDevice;
    }

    public boolean delete(String serialNumber) {
        if (deviceRepository.existsById(serialNumber)) {
            deviceRepository.deleteById(serialNumber);
            return true;
        }
        return false;
    }

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public Device findBySerialNumber(String serialNumber) {
        return deviceRepository.findById(serialNumber).orElseThrow();
    }

    public List<Device> findAllByModel(String model) {
        return deviceRepository.findAllByModel(model);
    }
}
