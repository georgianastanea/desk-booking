package com.springboot.DeskBooking.service;

import com.springboot.DeskBooking.entity.Office;
import com.springboot.DeskBooking.exceptions.CrudOperationException;
import com.springboot.DeskBooking.repository.OfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;


    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public Office getOfficeById(Long id) {
        Office office = officeRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Office does not exist!");
        });
        return office;
    }

    public Office updateOffice(Long id, boolean book){
        Office office = officeRepository.findById(id).orElseThrow(() ->{
            throw new CrudOperationException("Office does not exist!");
        });

        office.setAvailable(book);
        officeRepository.save(office);
        return office;
    }

    public List<Office> getAllOffices(){
        return (List<Office>) officeRepository.findAll();
    }
}
