package snappark.backend.service;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import snappark.backend.entity.Occupancy;
import snappark.backend.repository.OccupancyRepository;

@Service
@AllArgsConstructor
public class OccupancyServiceImpl implements OccupancyService {
    private OccupancyRepository occupancies;
    public Occupancy getOccupancy(Long parkId){ 
        return occupancies.findByParkId(parkId).orElse(null);    
    }

    public Occupancy createOccupancy(Occupancy Occupancy){
        return occupancies.save(Occupancy);
    };

    public Occupancy updateOccupancy(Long parkID, int change) {
        Optional<Occupancy> optionalOccupancy = occupancies.findByParkId(parkID);

        if (optionalOccupancy.isPresent()) {
            Occupancy occupancy = optionalOccupancy.get();
            int lotation=occupancy.getLotation();
            occupancy.setLotation(lotation+change); // Update the property
            return occupancies.save(occupancy); // Save the updated entity
        }

        // Handle case when the entity is not found
        return null;
    }

}
