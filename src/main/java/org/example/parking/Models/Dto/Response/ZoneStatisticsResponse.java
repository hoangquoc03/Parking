package org.example.parking.Models.Dto.Response;

public class ZoneStatisticsResponse {
    private Long id;
    private String name;
    private Integer capacity;
    private Integer occupiedSlots;
    private Integer availableSlots;


    public ZoneStatisticsResponse(Long id, String name, Integer capacity, Integer occupiedSlots, Integer availableSlots) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.occupiedSlots = occupiedSlots;
        this.availableSlots = availableSlots;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Integer getOccupiedSlots() { return occupiedSlots; }
    public void setOccupiedSlots(Integer occupiedSlots) { this.occupiedSlots = occupiedSlots; }
    public Integer getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(Integer availableSlots) { this.availableSlots = availableSlots; }
}