package com.springboot.DeskBooking.controller;

import com.springboot.DeskBooking.dto.OfficeDto;
import com.springboot.DeskBooking.entity.Office;
import com.springboot.DeskBooking.service.OfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offices")
public class OfficeController {

    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public ResponseEntity<List<OfficeDto>> getALlOffices(){
        return ResponseEntity.ok(officeService.getAllOffices());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<OfficeDto> getOfficeByID(@PathVariable Long id){
//        return ResponseEntity.ok(officeService.getOfficeById(id));
//    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeDto> updateOffice(@PathVariable Long id, @RequestBody boolean book){
        return ResponseEntity.ok(officeService.updateOffice(id,book));
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<OfficeDto>> getOfficesByDate(@PathVariable String date){
        return ResponseEntity.ok(officeService.getOfficesByDate(date));
    }


}
