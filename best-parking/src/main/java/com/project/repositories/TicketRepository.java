package com.project.repositories;

import com.project.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByVehicleAndStatus(Vehicle vehicle, TicketStatus status);
    List<Ticket> findByStatus(TicketStatus status);
}