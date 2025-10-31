package com.project.services;

import com.project.dto.TicketResponse;
import com.project.dto.VehicleEntryRequest;
import com.project.models.Ticket;
import com.project.models.TicketStatus;
import com.project.models.Vehicle;
import com.project.models.VehicleType;
import com.project.repositories.TicketRepository;
import com.project.repositories.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TicketService {

    private final VehicleRepository vehicleRepository;
    private final TicketRepository ticketRepository;

    public TicketService(VehicleRepository vehicleRepository, TicketRepository ticketRepository) {
        this.vehicleRepository = vehicleRepository;
        this.ticketRepository = ticketRepository;
    }

    public TicketResponse registerEntry(VehicleEntryRequest request) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(request.getLicensePlate().toUpperCase())
                .orElseGet(() -> vehicleRepository.save(
                        Vehicle.builder()
                                .licensePlate(request.getLicensePlate().toUpperCase())
                                .color(request.getColor())
                                .type(VehicleType.valueOf(request.getType().toUpperCase()))
                                .spaceCount(request.getSpaceCount())
                                .build()
                ));

        // Check if there’s already an active ticket
        ticketRepository.findByVehicleAndStatus(vehicle, TicketStatus.ACTIVE)
                .ifPresent(t -> { throw new RuntimeException("Vehicle already has an active ticket."); });

        Ticket ticket = Ticket.builder()
                .vehicle(vehicle)
                .entryTime(LocalDateTime.now())
                .status(TicketStatus.ACTIVE)
                .discountAmount(request.getDiscountAmount())
                .build();

        ticketRepository.save(ticket);

        return TicketResponse.builder()
                .licensePlate(vehicle.getLicensePlate())
                .type(vehicle.getType().name())
                .entryTime(ticket.getEntryTime())
                .status(ticket.getStatus().name())
                .build();
    }

    public TicketResponse registerExit(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Vehicle not found."));

        Ticket ticket = ticketRepository.findByVehicleAndStatus(vehicle, TicketStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("No active ticket found for this vehicle."));

        ticket.setExitTime(LocalDateTime.now());

        double amount = calculateFee(vehicle, ticket.getEntryTime(), ticket.getExitTime());
        if (ticket.getDiscountAmount() != null && ticket.getDiscountAmount() > 0) {
            amount -= ticket.getDiscountAmount();
        }

        ticket.setTotalAmount(Math.max(amount, 0)); // avoid negatives
        ticket.setStatus(TicketStatus.CLOSED);

        ticketRepository.save(ticket);

        return TicketResponse.builder()
                .licensePlate(vehicle.getLicensePlate())
                .type(vehicle.getType().name())
                .entryTime(ticket.getEntryTime())
                .exitTime(ticket.getExitTime())
                .totalAmount(ticket.getTotalAmount())
                .status(ticket.getStatus().name())
                .build();
    }
    public TicketResponse registerSpecialTicket(VehicleEntryRequest request, double manualAmount) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(request.getLicensePlate().toUpperCase())
                .orElseGet(() -> vehicleRepository.save(
                        Vehicle.builder()
                                .licensePlate(request.getLicensePlate().toUpperCase())
                                .color(request.getColor())
                                .type(VehicleType.valueOf(request.getType().toUpperCase()))
                                .spaceCount(request.getSpaceCount())
                                .build()
                ));

        ticketRepository.findByVehicleAndStatus(vehicle, TicketStatus.ACTIVE)
                .ifPresent(t -> { throw new RuntimeException("Vehicle already has an active ticket."); });

        Ticket ticket = Ticket.builder()
                .vehicle(vehicle)
                .entryTime(LocalDateTime.now())
                .exitTime(LocalDateTime.now())
                .status(TicketStatus.CLOSED)
                .totalAmount(manualAmount)
                .manualAmount(manualAmount)
                .specialTicket(true)
                .build();

        ticketRepository.save(ticket);

        return TicketResponse.builder()
                .licensePlate(vehicle.getLicensePlate())
                .type(vehicle.getType().name())
                .entryTime(ticket.getEntryTime())
                .exitTime(ticket.getExitTime())
                .totalAmount(ticket.getTotalAmount())
                .status(ticket.getStatus().name())
                .build();
    }
    private double calculateFee(Vehicle v, LocalDateTime entry, LocalDateTime exit) {
        long minutes = Duration.between(entry, exit).toMinutes();
        double hours = Math.ceil(minutes / 60.0);

        double rate;
        switch (v.getType()) {
            case MOTORCYCLE -> rate = 2.5;
            case CAR -> rate = 3.5;
            case LARGE -> rate = 3.5 * v.getSpaceCount();
            default -> rate = 3.5;
        }
        return hours * rate;
    }

    public List<TicketResponse> listAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(t -> TicketResponse.builder()
                        .licensePlate(t.getVehicle().getLicensePlate())
                        .type(t.getVehicle().getType().name())
                        .entryTime(t.getEntryTime())
                        .exitTime(t.getExitTime())
                        .totalAmount(t.getTotalAmount())
                        .status(t.getStatus().name())
                        .build())
                .collect(Collectors.toList());
    }
}