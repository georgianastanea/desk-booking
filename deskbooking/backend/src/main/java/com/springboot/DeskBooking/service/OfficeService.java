package com.springboot.DeskBooking.service;

import com.springboot.DeskBooking.dto.BookingDto;
import com.springboot.DeskBooking.dto.OfficeDto;
import com.springboot.DeskBooking.entity.Booking;
import com.springboot.DeskBooking.entity.Office;
import com.springboot.DeskBooking.exceptions.CrudOperationException;
import com.springboot.DeskBooking.repository.OfficeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;


    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public OfficeDto getOfficeById(Long id) {
        Office office = officeRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Office does not exist!");
        });
        return new ModelMapper().map(office,OfficeDto.class);
    }

    public OfficeDto updateOffice(Long id, boolean book){
        Office office = officeRepository.findById(id).orElseThrow(() ->{
            throw new CrudOperationException("Office does not exist!");
        });

        office.setAvailable(book);
        officeRepository.save(office);
        return new ModelMapper().map(office,OfficeDto.class);
    }

    public List<OfficeDto> getAllOffices(){

        Iterable<Office> iterableOffices = officeRepository.findAll();
        List<OfficeDto> offices = new ArrayList<>();

        iterableOffices.forEach(office -> offices.add(new ModelMapper().map(office,OfficeDto.class)));

        return offices;
    }
}
