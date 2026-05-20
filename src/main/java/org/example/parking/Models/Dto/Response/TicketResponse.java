package org.example.parking.Models.Dto.Response;

import java.time.LocalDateTime;

public class TicketResponse {
    private Long id;
    private String licensePlate;
    private String zoneName;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    public TicketResponse(Long id, String licensePlate, String zoneName, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.zoneName = zoneName;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }
    public LocalDateTime getCheckInTime() { return checkInTime; }
    public void setCheckInTime(LocalDateTime checkInTime) { this.checkInTime = checkInTime; }
    public LocalDateTime getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(LocalDateTime checkOutTime) { this.checkOutTime = checkOutTime; }
}