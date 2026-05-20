package org.example.parking.Repositories;


import org.example.parking.Models.Dto.Response.TicketSummaryResponse;
import org.example.parking.Models.Entity.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {


    Optional<ParkingTicket> findFirstByVehicleIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(Long vehicleId);

    boolean existsByVehicleIdAndCheckOutTimeIsNull(Long vehicleId);
    @Query("SELECT new com.example.parking.dto.response.TicketSummaryResponse(" +
            "t.id, v.licensePlate, z.name, t.checkInTime, t.checkOutTime) " +
            "FROM ParkingTicket t " +
            "JOIN t.vehicle v " +
            "JOIN t.zone z " +
            "WHERE t.checkInTime >= :startOfDay AND t.checkInTime <= :endOfDay")
    List<TicketSummaryResponse> findTicketsInDay(@Param("startOfDay") LocalDateTime startOfDay,
                                                 @Param("endOfDay") LocalDateTime endOfDay);


}