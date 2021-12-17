package com.veterok.sensorapi.mapper;


import com.veterok.sensorapi.model.State;
import com.veterok.sensorapi.model.light.StateLightDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper {
    State toEntity(StateLightDto dto);
}
