package org.example.parking.Services;


import org.example.parking.Models.Dto.Request.TicketRequest;
import org.example.parking.Models.Dto.Response.TicketResponse;
import org.example.parking.Models.Entity.ParkingTicket;
import org.example.parking.Models.Entity.Vehicle;
import org.example.parking.Models.Entity.Zone;
import org.example.parking.Repositories.ParkingTicketRepository;
import org.example.parking.Repositories.VehicleRepository;
import org.example.parking.Repositories.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class ParkingService {

    private final ParkingTicketRepository ticketRepository;
    private final VehicleRepository vehicleRepository;
    private final ZoneRepository zoneRepository;

    public ParkingService(ParkingTicketRepository ticketRepository, VehicleRepository vehicleRepository, ZoneRepository zoneRepository) {
        this.ticketRepository = ticketRepository;
        this.vehicleRepository = vehicleRepository;
        this.zoneRepository = zoneRepository;
    }


    @Transactional
    public TicketResponse checkIn(TicketRequest req) {

        Vehicle vehicle = vehicleRepository.findById(req.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phương tiện với ID: " + req.getVehicleId()));


        Zone zone = zoneRepository.findById(req.getZoneId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khu vực đỗ xe với ID: " + req.getZoneId()));


        if (ticketRepository.existsByVehicleIdAndCheckOutTimeIsNull(req.getVehicleId())) {
            throw new RuntimeException("Phương tiện này hiện đang ở trong bãi xe, không thể tiếp tục check-in!");
        }

        if (zone.getOccupiedSpots() >= zone.getCapacity()) {
            throw new RuntimeException("Khu vực " + zone.getName() + " đã hết chỗ đỗ!");
        }


        ParkingTicket ticket = new ParkingTicket();
        ticket.setVehicle(vehicle);
        ticket.setZone(zone);
        ticket.setCheckInTime(LocalDateTime.now());


        zone.setOccupiedSpots(zone.getOccupiedSpots() + 1);
        zoneRepository.save(zone);


        ParkingTicket savedTicket = ticketRepository.save(ticket);


        return new TicketResponse(
                savedTicket.getId(),
                vehicle.getLicensePlate(),
                zone.getName(),
                savedTicket.getCheckInTime(),
                savedTicket.getCheckOutTime()
        );
    }


    @Transactional
    public TicketResponse checkOut(Long vehicleId) {

        ParkingTicket ticket = ticketRepository.findFirstByVehicleIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(vehicleId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lượt gửi xe hợp lệ (chưa xuất bãi) cho xe này!"));


        ticket.setCheckOutTime(LocalDateTime.now());


        Zone zone = ticket.getZone();
        if (zone.getOccupiedSpots() > 0) {
            zone.setOccupiedSpots(zone.getOccupiedSpots() - 1);
            zoneRepository.save(zone);
        }


        ParkingTicket updatedTicket = ticketRepository.save(ticket);

        return new TicketResponse(
                updatedTicket.getId(),
                ticket.getVehicle().getLicensePlate(),
                zone.getName(),
                updatedTicket.getCheckInTime(),
                updatedTicket.getCheckOutTime()
        );
    }
}