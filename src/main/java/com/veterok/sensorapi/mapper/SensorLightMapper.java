package com.veterok.sensorapi.mapper;

import com.veterok.sensorapi.model.Sensor;
import com.veterok.sensorapi.model.light.SensorLightDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SensorLightMapper {

    SensorLightDto makeItLight(Sensor sensor);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target="longitude", source="longitude")
    @Mapping(target="latitude", source="latitude")
    Sensor updateCoordinates(@MappingTarget Sensor target, SensorLightDto source);
}
