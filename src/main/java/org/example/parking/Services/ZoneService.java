package org.example.parking.Services;

import org.example.parking.Models.Dto.Response.ZoneStatisticsResponse;
import org.example.parking.Models.Entity.Zone;
import org.example.parking.Repositories.ZoneRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;

    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    // Cách 1: Vòng lặp Java ở tầng Service
    public List<ZoneStatisticsResponse> getStatsV1() {
        List<Zone> zones = zoneRepository.findAll();
        List<ZoneStatisticsResponse> responseList = new ArrayList<>();

        for (Zone zone : zones) {
            int availableSlots = zone.getCapacity() - zone.getOccupiedSpots(); // Tính toán thủ công
            ZoneStatisticsResponse dto = new ZoneStatisticsResponse(
                    zone.getId(),
                    zone.getName(),
                    zone.getCapacity(),
                    zone.getOccupiedSpots(),
                    availableSlots
            );
            responseList.add(dto);
        }
        return responseList;
    }

    // Cách 2: Lấy dữ liệu đã tối ưu từ JPQL nâng cao
    public List<ZoneStatisticsResponse> getStatsV2() {
        return zoneRepository.getZoneStatisticsByJpql();
    }
}