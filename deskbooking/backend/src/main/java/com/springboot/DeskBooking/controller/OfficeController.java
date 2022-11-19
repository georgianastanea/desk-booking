package com.springboot.DeskBooking.controller;

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
    public ResponseEntity<List<Office>> getALlOffices(){
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Office> getOfficeByID(@PathVariable Long id){
        return ResponseEntity.ok(officeService.getOfficeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Office> updateOffice(@PathVariable Long id, @RequestBody boolean book){
        return ResponseEntity.ok(officeService.updateOffice(id,book));
    }

}
