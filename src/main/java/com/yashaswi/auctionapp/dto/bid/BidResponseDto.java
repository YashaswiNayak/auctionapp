package com.yashaswi.auctionapp.dto.bid;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BidResponseDto {
    Integer bidId;
    Double amount;
    String auctionName;
    Integer auctionId;
    Integer ownerId;
    String ownerName;
}
