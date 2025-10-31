package com.project.controllers;

import com.project.dto.TicketResponse;
import com.project.dto.VehicleEntryRequest;
import com.project.services.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/entry")
    public TicketResponse registerEntry(@RequestBody VehicleEntryRequest request) {
        return ticketService.registerEntry(request);
    }

    @PutMapping("/exit/{licensePlate}")
    public TicketResponse registerExit(@PathVariable String licensePlate) {
        return ticketService.registerExit(licensePlate);
    }

    @PostMapping("/special")
    public TicketResponse registerSpecialTicket(@RequestBody VehicleEntryRequest request,
                                                @RequestParam double manualAmount) {
        return ticketService.registerSpecialTicket(request, manualAmount);
    }

    @GetMapping
    public List<TicketResponse> getAllTickets() {
        return ticketService.listAllTickets();
    }
}