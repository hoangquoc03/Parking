package org.example.parking.Controllers;

import org.example.parking.Models.Dto.Response.ApiResponse;
import org.example.parking.Models.Dto.Response.ZoneStatisticsResponse;
import org.example.parking.Services.ZoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }


    @GetMapping("/api/v1/zones/stats")
    public ResponseEntity<ApiResponse<List<ZoneStatisticsResponse>>> getStatsV1() {
        long startTime = System.nanoTime();

        List<ZoneStatisticsResponse> data = zoneService.getStatsV1();

        long endTime = System.nanoTime();
        System.out.println(">>> [API V1 - Java Loop] Thời gian xử lý: " + (endTime - startTime) + " ns");

        ApiResponse<List<ZoneStatisticsResponse>> response = new ApiResponse<>(true, "Lấy thống kê V1 thành công", data);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/api/v2/zones/stats")
    public ResponseEntity<ApiResponse<List<ZoneStatisticsResponse>>> getStatsV2() {
        long startTime = System.nanoTime();

        List<ZoneStatisticsResponse> data = zoneService.getStatsV2();

        long endTime = System.nanoTime();
        System.out.println(">>> [API V2 - JPQL Projection] Thời gian xử lý: " + (endTime - startTime) + " ns");

        ApiResponse<List<ZoneStatisticsResponse>> response = new ApiResponse<>(true, "Lấy thống kê V2 thành công", data);
        return ResponseEntity.ok(response);
    }
}