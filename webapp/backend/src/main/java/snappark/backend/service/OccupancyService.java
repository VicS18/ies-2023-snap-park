package snappark.backend.service;

import snappark.backend.entity.Occupancy;
import snappark.backend.entity.Occupancy.OccupancyId;

public interface OccupancyService {
    Occupancy getOccupancy(Long parkId);

    Occupancy createOccupancy(Occupancy Occupancy);

    Occupancy updateOccupancy(OccupancyId occupancyId, int newLotation);
}
