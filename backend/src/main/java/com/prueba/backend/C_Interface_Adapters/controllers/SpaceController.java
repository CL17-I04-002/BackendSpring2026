package com.prueba.backend.C_Interface_Adapters.controllers;

import com.prueba.backend.A_Domain.business.Space;
import com.prueba.backend.B_Use_Cases.Services.Interface.ISpaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spaces")
@AllArgsConstructor
public class SpaceController {
    private final ISpaceService spaceService;

    @PostMapping
    public ResponseEntity<Space> createSpace(@RequestBody Space space) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spaceService.saveSpace(space));
    }

    @GetMapping
    public ResponseEntity<List<Space>> getAllSpaces() {
        return ResponseEntity.ok(spaceService.findAllSpaces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Space> getSpaceById(@PathVariable Long id) {
        return spaceService.findSpaceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Space> updateSpace(@RequestBody Space space,
                                             @PathVariable Long id) {
        return ResponseEntity.ok(spaceService.updateSpace(space, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable Long id) {
        spaceService.deleteSpace(id);
        return ResponseEntity.noContent().build();
    }
}