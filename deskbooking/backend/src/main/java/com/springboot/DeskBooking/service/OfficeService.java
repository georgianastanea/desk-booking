package com.springboot.DeskBooking.service;

import com.springboot.DeskBooking.dto.BookingHistoryDto;
import com.springboot.DeskBooking.dto.OfficeDto;
import com.springboot.DeskBooking.entity.Office;
import com.springboot.DeskBooking.exceptions.CrudOperationException;
import com.springboot.DeskBooking.repository.OfficeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;

    private HistoryService historyService;

    public OfficeService(OfficeRepository officeRepository, HistoryService historyService) {
        this.officeRepository = officeRepository;

        this.historyService = historyService;
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

        return offices.stream().sorted(Comparator.comparing(OfficeDto::getId)).collect(Collectors.toList());
    }

    public List<OfficeDto> getOfficesByDate(String date){

        String initialDate = date.substring(6,21);
        String finalDate = initialDate.substring(3,6) + " " + initialDate.substring(6,9) +
                " " + initialDate.substring(9,11) + " " + initialDate.substring(11);


        List<BookingHistoryDto> history = historyService.getHistory();

        List<Long> bookedOffices = history.stream()
                .filter(e -> e.getDate().equals(finalDate))
                .map(BookingHistoryDto::getOfficeNumber).toList();

        List<OfficeDto> allOffices = getAllOffices();

        allOffices.forEach(office -> {
            if(bookedOffices.contains(office.getNumber()))
                office.setAvailable(false);
            else{
                office.setAvailable(true);
            }
        });

        return allOffices;


    }
}
