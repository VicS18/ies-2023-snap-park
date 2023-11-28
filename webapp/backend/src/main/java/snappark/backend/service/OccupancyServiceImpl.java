package snappark.backend.service;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import snappark.backend.entity.Occupancy.OccupancyId;
import snappark.backend.entity.Occupancy;
import snappark.backend.entity.Park;
import snappark.backend.repository.OccupancyRepository;
import snappark.backend.repository.ParkRepository;

@Service
@AllArgsConstructor
public class OccupancyServiceImpl implements OccupancyService {
    private OccupancyRepository occupancies;
    private ParkRepository parks;
    public Occupancy getOccupancy(Long parkId){ 
        Park park=parks.findById(parkId).orElse(null);
        Occupancy occupancy = new Occupancy();
        OccupancyId occupancyId = occupancy.new OccupancyId(park);

        return occupancies.findById(occupancyId).orElse(null);    
    }

    public Occupancy createOccupancy(Occupancy Occupancy){
        return occupancies.save(Occupancy);
    };

    public Occupancy updateOccupancy(OccupancyId occupancyId, int change) {
        Optional<Occupancy> optionalOccupancy = occupancies.findById(occupancyId);

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
