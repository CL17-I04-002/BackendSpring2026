package com.prueba.backend.C_Interface_Adapters;

import com.prueba.backend.A_Domain.business.Space;
import com.prueba.backend.B_Use_Cases.Services.Interface.ISpaceService;
import com.prueba.backend.C_Interface_Adapters.controllers.SpaceController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

class SpaceControllerTest {
    @Mock
    private ISpaceService spaceService;

    @InjectMocks
    private SpaceController controller;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateSpace() {

        Space space = new Space();

        Mockito.when(spaceService.saveSpace(space)).thenReturn(space);

        ResponseEntity<Space> response = controller.createSpace(space);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(space, response.getBody());

        Mockito.verify(spaceService).saveSpace(space);
    }

    @Test
    void shouldReturnAllSpaces() {

        List<Space> spaces = List.of(new Space(), new Space());

        Mockito.when(spaceService.findAllSpaces()).thenReturn(spaces);

        ResponseEntity<List<Space>> response = controller.getAllSpaces();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(spaces, response.getBody());

        Mockito.verify(spaceService).findAllSpaces();
    }

    @Test
    void shouldReturnSpaceById() {

        Space space = new Space();

        Mockito.when(spaceService.findSpaceById(1L))
                .thenReturn(Optional.of(space));

        ResponseEntity<Space> response = controller.getSpaceById(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(space, response.getBody());

        Mockito.verify(spaceService).findSpaceById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenSpaceDoesNotExist() {

        Mockito.when(spaceService.findSpaceById(1L))
                .thenReturn(Optional.empty());

        ResponseEntity<Space> response = controller.getSpaceById(1L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(spaceService).findSpaceById(1L);
    }

    @Test
    void shouldUpdateSpace() {

        Space request = new Space();
        Space updated = new Space();

        Mockito.when(spaceService.updateSpace(request, 1L))
                .thenReturn(updated);

        ResponseEntity<Space> response = controller.updateSpace(request, 1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(updated, response.getBody());

        Mockito.verify(spaceService).updateSpace(request, 1L);
    }

    @Test
    void shouldDeleteSpace() {

        Mockito.doNothing().when(spaceService).deleteSpace(1L);

        ResponseEntity<Void> response = controller.deleteSpace(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        Mockito.verify(spaceService).deleteSpace(1L);
    }
}