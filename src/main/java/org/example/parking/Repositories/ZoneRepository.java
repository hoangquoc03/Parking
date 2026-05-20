package org.example.parking.Repositories;

import org.example.parking.Models.Dto.Response.ZoneStatisticsResponse;
import org.example.parking.Models.Entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    @Query("SELECT new org.example.parking.Models.Dto.Response.ZoneStatisticsResponse(" +
            "z.id, z.name, z.capacity, z.occupiedSpots, (z.capacity - z.occupiedSpots)) " +
            "FROM Zone z")
    List<ZoneStatisticsResponse> getZoneStatisticsByJpql();
}