package com.storage.core.repository;

import com.storage.core.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {
    List<Device> findAllByModel(String model);
}
