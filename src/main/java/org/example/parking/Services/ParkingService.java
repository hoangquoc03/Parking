package org.example.parking.Services;


import org.example.parking.Models.Dto.Request.TicketRequest;
import org.example.parking.Models.Dto.Response.PageResponse;
import org.example.parking.Models.Dto.Response.TicketResponse;
import org.example.parking.Models.Dto.Response.TicketSummaryResponse;
import org.example.parking.Models.Entity.ParkingTicket;
import org.example.parking.Models.Entity.Vehicle;
import org.example.parking.Models.Entity.Zone;
import org.example.parking.Repositories.ParkingTicketRepository;
import org.example.parking.Repositories.VehicleRepository;
import org.example.parking.Repositories.ZoneRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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


    public List<TicketSummaryResponse> getDailyTicketSummary() {
        // Lấy thời điểm đầu ngày (00:00:00) và cuối ngày (23:59:59.999999999) của ngày hiện tại
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);

        return ticketRepository.findTicketsInDay(startOfDay, endOfDay);
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
    public PageResponse<TicketResponse> getTicketHistory(
            String licensePlate,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            int page,
            int size
    ) {
        // Xử lý an toàn chỉ số trang
        if (page < 0) {
            page = 0;
        }

        // Tạo cấu hình Pageable: Mặc định sắp xếp theo thời gian xe vào mới nhất (DESC)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "checkInTime"));

        // Thực hiện truy vấn lọc từ database
        Page<TicketResponse> ticketPage = ticketRepository.findTicketHistory(
                (licensePlate != null && !licensePlate.trim().isEmpty()) ? licensePlate : null,
                fromDate,
                toDate,
                pageable
        );

        // Chuẩn hóa dữ liệu trả về thông qua lớp wrapper PageResponse
        return new PageResponse<>(
                ticketPage.getContent(),
                ticketPage.getNumber(),
                ticketPage.getSize(),
                ticketPage.getTotalElements(),
                ticketPage.getTotalPages(),
                ticketPage.isLast()
        );
    }
}