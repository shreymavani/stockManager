package com.mahavir.cosmetic.controller;

import com.mahavir.cosmetic.model.Inward;
import com.mahavir.cosmetic.service.InwardService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inwards")
public class InwardController {

    @Autowired
    private InwardService inwardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inward createTask(@RequestBody Inward inward){
        inward.setItemEntry(UUID.randomUUID().toString().split("-")[0]);
        return inwardService.addInward(inward);
    }

    @GetMapping
    public List<Inward> getInwards() {
        return inwardService.findAllInwards();
    }

    @GetMapping("/{date}")
    public List<Inward> getInwardsForDate(@PathVariable String date){
        return inwardService.getInwardByDate(date);
    }

    @DeleteMapping("/{taskId}")
    public String deleteInward(@PathVariable String itemId){
        return inwardService.deleteTask(itemId);
    }

    @DeleteMapping
    public String deleteAllInwards(){
        return inwardService.deleteAll();
    }
}
