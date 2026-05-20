package org.example.parking.Controllers;


import org.example.parking.Models.Dto.Request.TicketRequest;
import org.example.parking.Models.Dto.Response.ApiResponse;
import org.example.parking.Models.Dto.Response.TicketResponse;
import org.example.parking.Services.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tickets")
public class ParkingTicketController {

    private final ParkingService parkingService;

    public ParkingTicketController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }


    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<TicketResponse>> checkIn(@RequestBody TicketRequest request) {
        try {
            TicketResponse data = parkingService.checkIn(request);
            ApiResponse<TicketResponse> response = new ApiResponse<>(true, "Xe vào bãi thành công", data);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<TicketResponse> response = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/check-out/{vehicleId}")
    public ResponseEntity<ApiResponse<TicketResponse>> checkOut(@PathVariable("vehicleId") Long vehicleId) {
        try {
            TicketResponse data = parkingService.checkOut(vehicleId);
            ApiResponse<TicketResponse> response = new ApiResponse<>(true, "Xe ra bãi thành công", data);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<TicketResponse> response = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
}