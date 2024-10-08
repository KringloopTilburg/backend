package nl.kringlooptilburg.businessservice.mappers.impl;

import nl.kringlooptilburg.businessservice.domain.dto.BusinessDto;
import nl.kringlooptilburg.businessservice.domain.entities.Business;
import nl.kringlooptilburg.businessservice.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BusinessMapperImpl implements Mapper<Business, BusinessDto> {

    private final ModelMapper modelMapper;

    public BusinessMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BusinessDto mapTo(Business businessEntity) {
        return modelMapper.map(businessEntity, BusinessDto.class);
    }

    @Override
    public Business mapFrom(BusinessDto dto) {
        return modelMapper.map(dto, Business.class);
    }
}
