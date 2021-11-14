package com.storage.core.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.storage.core.model.Device;
import com.storage.core.repository.DeviceRepository;

import java.util.List;

@ChangeLog(order = "001")
public class DBInitChangelog {

    @ChangeSet(order = "001", id = "init_devices", author = "bogdan")
    public void initDevices(DeviceRepository deviceRepository) {
        deviceRepository.saveAll(
                List.of(
                        new Device("5623-KU", "GalaxyS9", "Samsung Galaxy S9"),
                        new Device("246277C3", "20HA", "ThinkPad T410"),
                        new Device("C36B7811", "Z2XQ", "ThinkStation P620")
                )
        );
    }
}
