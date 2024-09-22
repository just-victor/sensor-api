package com.veterok.sensorapi.mapper;


import com.veterok.sensorapi.model.State;
import com.veterok.sensorapi.model.light.StateLightDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sensorId", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    State toEntity(StateLightDto dto);
}
