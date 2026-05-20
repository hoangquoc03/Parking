package org.example.parking.Controllers;


import org.example.parking.Models.Dto.Request.VehicleCreateRequest;
import org.example.parking.Models.Dto.Response.ApiResponse;
import org.example.parking.Models.Dto.Response.PageResponse;
import org.example.parking.Models.Dto.Response.VehicleResponse;
import org.example.parking.Models.Entity.Vehicle;
import org.example.parking.Services.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Vehicle>> createVehicle(@RequestBody VehicleCreateRequest request) {
        Vehicle newVehicle = vehicleService.createVehicle(request);
        ApiResponse<Vehicle> response = new ApiResponse<>(true, "Tạo phương tiện thành công", newVehicle);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<VehicleResponse>>> getVehicles(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        PageResponse<VehicleResponse> pageData = vehicleService.getPagedVehicles(page, size, sortBy, direction, keyword);
        ApiResponse<PageResponse<VehicleResponse>> response = new ApiResponse<>(true, "Lấy danh sách thành công", pageData);
        return ResponseEntity.ok(response);
    }
}