package org.example.parking.Repositories;

import org.example.parking.Models.Dto.Response.VehicleResponse;
import org.example.parking.Models.Entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v.id as id, v.licensePlate as licensePlate, v.color as color, v.type as vehicleType " +
            "FROM Vehicle v " +
            "WHERE :keyword IS NULL " +
            "OR LOWER(v.licensePlate) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(v.color) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<VehicleResponse> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}