package com.yashaswi.auctionapp.dto.auction;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AuctionCreationDto {
    String title;
    String description;
    Double startingPrice;
    Double minimumIncrement;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
