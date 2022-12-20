package com.dev.segbaya.controller;

import com.dev.segbaya.domain.PublishHouse;
import com.dev.segbaya.service.PublishHouseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/publish-house")
@AllArgsConstructor
public class PublishHouseController {

    private final PublishHouseService publishHouseService;

    @GetMapping("/find/{publishHouseId}")
    public ResponseEntity<PublishHouse> getPublishHouse(@PathVariable ("publishHouseId") Long publishHouseId){
        return ResponseEntity.ok().body(publishHouseService.getPublishHouseById(publishHouseId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PublishHouse>> getPublishHouses(){
        return ResponseEntity.ok().body(publishHouseService.getPublishHouses());
    }



    @PostMapping("/register")
    public ResponseEntity<PublishHouse> savePublishHouse(@RequestBody PublishHouse publishHouse){
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().
                path("/api/publish-house/register").toUriString());
        return ResponseEntity.created(uri).body(publishHouseService.savePublishHouse(publishHouse));
    }

    @PutMapping("/update/{publishHouseId}")
    public ResponseEntity<PublishHouse> updatePublishHouse(@PathVariable ("publishHouseId") Long publishHouseId,
                                                @RequestBody PublishHouse publishHouse){
        publishHouseService.updatePublishHouse(publishHouseId, publishHouse);
        return ResponseEntity.ok().body( publishHouseService.updatePublishHouse(publishHouseId, publishHouse));
    }

    @DeleteMapping("/delete/{publishHouseId}")
    public ResponseEntity<?> deletePublishHouse(@PathVariable ("publishHouseId") Long publishHouseId){
        publishHouseService.deletePublishHouse(publishHouseId);
        return ResponseEntity.ok().body("Deleted successfully !");
    }

}
