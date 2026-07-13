package com.prueba.backend.B_Use_Cases.Services.Implementation;

import com.prueba.backend.A_Domain.business.Space;
import com.prueba.backend.B_Use_Cases.Exception.ObjectNotFoundException;
import com.prueba.backend.B_Use_Cases.Services.Interface.ISpaceService;
import com.prueba.backend.D_Infraestructure.repository.SpaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpaceServiceImpl implements ISpaceService {
    private final SpaceRepository spaceRepository;

    @Override
    public Space saveSpace(Space space) {
        return spaceRepository.save(space);
    }

    @Override
    public List<Space> findAllSpaces() {
        return spaceRepository.findAll();
    }

    @Override
    public Optional<Space> findSpaceById(Long id) {
        return spaceRepository.findById(id);
    }

    @Override
    public Space updateSpace(Space space, Long id) {
        Space spaceToUpdate = spaceRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Space not found"));

        spaceToUpdate.setName(space.getName());
        spaceToUpdate.setType(space.getType());
        spaceToUpdate.setCapacity(space.getCapacity());
        spaceToUpdate.setUpdatedAt(space.getUpdatedAt());
        spaceToUpdate.setHourlyRate(space.getHourlyRate());
        spaceToUpdate.setCreatedAt(space.getCreatedAt());
        spaceToUpdate.setLocation(space.getLocation());

        return spaceToUpdate;
    }

    @Override
    public void deleteSpace(Long id) {
        Space spaceToDelete = spaceRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Space not found"));
        spaceRepository.delete(spaceToDelete);
    }
}
