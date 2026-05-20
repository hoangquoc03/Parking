package org.example.parking.Services;

import org.example.parking.Models.Dto.Request.VehicleCreateRequest;
import org.example.parking.Models.Dto.Response.PageResponse;
import org.example.parking.Models.Dto.Response.VehicleResponse;
import org.example.parking.Models.Entity.Vehicle;
import org.example.parking.Repositories.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }


    public Vehicle createVehicle(VehicleCreateRequest request) {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setColor(request.getColor());
        vehicle.setType(request.getType());
        return vehicleRepository.save(vehicle);
    }


    public PageResponse<VehicleResponse> getPagedVehicles(int page, int size, String sortBy, String direction, String keyword) {

        if (page < 0) {
            page = 0;
        }


        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.trim().isEmpty()) {
            Sort.Direction dir = (direction != null && direction.equalsIgnoreCase("DESC"))
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            sort = Sort.by(dir, sortBy);
        }


        Pageable pageable = PageRequest.of(page, size, sort);


        Page<VehicleResponse> vehiclePage = vehicleRepository.findAllByKeyword(keyword, pageable);


        return new PageResponse<>(
                vehiclePage.getContent(),
                vehiclePage.getNumber(),
                vehiclePage.getSize(),
                vehiclePage.getTotalElements(),
                vehiclePage.getTotalPages(),
                vehiclePage.isLast()
        );
    }
}
