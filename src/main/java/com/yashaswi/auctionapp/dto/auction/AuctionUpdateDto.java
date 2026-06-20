package com.yashaswi.auctionapp.dto.auction;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuctionUpdateDto {
    private String title;
    private String description;
    private Double minimumIncrement;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}