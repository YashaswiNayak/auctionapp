package com.yashaswi.auctionapp.dto.auction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionResponseDto {
    Integer id;
    String title;
    String description;
    Double startingPrice;
    Double minimumIncrement;
    String ownerUsername;
    Integer ownerId;
}
