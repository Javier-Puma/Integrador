package com.project.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private String licensePlate;
    private String type;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Double totalAmount;
    private String status;
}