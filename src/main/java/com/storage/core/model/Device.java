package com.storage.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Pattern;

@Document(collection = "device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @Pattern(regexp = "^[\\w\\d\\s-]+$")
    private String serialNumber;
    @Pattern(regexp = "^[\\w\\d]+$")
    private String model;
    private String description;
}
