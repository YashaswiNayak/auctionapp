package com.yashaswi.auctionapp.mapper;

import com.yashaswi.auctionapp.dto.auction.AuctionResponseDto;
import com.yashaswi.auctionapp.dto.bid.BidResponseDto;
import com.yashaswi.auctionapp.dto.user.UserResponseDto;
import com.yashaswi.auctionapp.entity.Auction;
import com.yashaswi.auctionapp.entity.Bid;
import com.yashaswi.auctionapp.entity.User;

public class EntityToDtoMapper {
    private EntityToDtoMapper() {

    }

    public static UserResponseDto toDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }

    public static AuctionResponseDto toDto(Auction auction) {
        AuctionResponseDto auctionResponseDto = new AuctionResponseDto();
        auctionResponseDto.setId(auction.getId());
        auctionResponseDto.setTitle(auction.getTitle());
        auctionResponseDto.setDescription(auction.getDescription());
        auctionResponseDto.setStatus(auction.getStatus());
        auctionResponseDto.setStartingPrice(auction.getStartingPrice());
        auctionResponseDto.setMinimumIncrement(auction.getMinimumIncrement());
        auctionResponseDto.setOwnerId(auction.getUser().getId());
        auctionResponseDto.setOwnerUsername(auction.getUser().getUsername());
        return auctionResponseDto;
    }

    public static BidResponseDto toDto(Bid bid) {
        BidResponseDto bidResponseDto = new BidResponseDto();
        bidResponseDto.setBidId(bid.getId());
        bidResponseDto.setAmount(bid.getAmount());
        bidResponseDto.setAuctionId(bid.getAuction().getId());
        bidResponseDto.setAuctionName(bid.getAuction().getTitle());
        bidResponseDto.setOwnerId(bid.getUser().getId());
        bidResponseDto.setOwnerName(bid.getUser().getUsername());
        return bidResponseDto;
    }
}
