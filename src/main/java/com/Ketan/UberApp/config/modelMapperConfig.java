package com.Ketan.UberApp.config;

import com.Ketan.UberApp.dto.PointDto;
import com.Ketan.UberApp.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class modelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(PointDto.class, Point.class).setConverter(
                convertor -> {
                        PointDto pointDto = convertor.getSource();
                        return GeometryUtil.createPoint(pointDto);
                    }
                );

        mapper.typeMap(Point.class, PointDto.class).setConverter(
                context -> {
                    Point point = context.getSource();
                    Double[] coordinates={
                            point.getX(),
                            point.getY()
                    };
                    return new PointDto(coordinates);
                }
        );

        return mapper;
    }

}
