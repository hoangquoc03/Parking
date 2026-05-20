package org.example.parking.Models.Dto.Request;

public class TicketRequest {
    private Long vehicleId;
    private Long zoneId;

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
    public Long getZoneId() { return zoneId; }
    public void setZoneId(Long zoneId) { this.zoneId = zoneId; }
}
