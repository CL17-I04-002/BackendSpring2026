package com.prueba.backend.C_Interface_Adapters.controllers;

import com.prueba.backend.A_Domain.business.Space;
import com.prueba.backend.B_Use_Cases.Services.Interface.ISpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spaces")
@AllArgsConstructor
@Tag(
        name = "Spaces",
        description = "Operations related to coworking spaces"
)
public class SpaceController {
    private final ISpaceService spaceService;

    @Operation(
            summary = "Create a new space",
            description = "Creates a coworking space. Only ADMIN users can execute this operation."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Space successfully created"
    )
    @PostMapping
    public ResponseEntity<Space> createSpace(@RequestBody Space space) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spaceService.saveSpace(space));
    }

    @Operation(
            summary = "Get all spaces",
            description = "Returns all available coworking spaces."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Spaces retrieved successfully"
    )
    @GetMapping
    public ResponseEntity<List<Space>> getAllSpaces() {

        return ResponseEntity.ok(spaceService.findAllSpaces());
    }


    @Operation(
            summary = "Find space by id",
            description = "Returns a coworking space by identifier."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Space found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Space not found"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Space> getSpaceById(
            @PathVariable Long id) {

        return spaceService.findSpaceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(
            summary = "Update space",
            description = "Updates an existing coworking space."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Space updated successfully"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Space> updateSpace(
            @RequestBody Space space,
            @PathVariable Long id) {

        return ResponseEntity.ok(
                spaceService.updateSpace(space,id)
        );
    }


    @Operation(
            summary = "Delete space",
            description = "Deletes a coworking space. Only ADMIN users can execute this operation."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Space deleted successfully"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(
            @PathVariable Long id) {

        spaceService.deleteSpace(id);

        return ResponseEntity.noContent().build();
    }

}