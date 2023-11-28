package snappark.backend.service;

import snappark.backend.entity.Occupancy;

public interface OccupancyService {
    Occupancy getOccupancy(Long parkId);

    Occupancy createOccupancy(Occupancy Occupancy);

    Occupancy updateOccupancy(Long parkID, int newLotation);
}
