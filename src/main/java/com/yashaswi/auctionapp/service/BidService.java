package com.yashaswi.auctionapp.service;

import com.yashaswi.auctionapp.dto.bid.BidCreationDto;
import com.yashaswi.auctionapp.dto.bid.BidResponseDto;
import com.yashaswi.auctionapp.entity.Auction;
import com.yashaswi.auctionapp.entity.Bid;
import com.yashaswi.auctionapp.entity.User;
import com.yashaswi.auctionapp.enums.AuctionStatus;
import com.yashaswi.auctionapp.exception.AuctionNotFoundException;
import com.yashaswi.auctionapp.mapper.EntityToDtoMapper;
import com.yashaswi.auctionapp.repository.AuctionRepository;
import com.yashaswi.auctionapp.repository.BidRepository;
import com.yashaswi.auctionapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    @Transactional
    public BidResponseDto placeBid(Integer auctionId, BidCreationDto bidCreationDto, String userName) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionNotFoundException("Auction not found"));
        User bidder = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User not found " + userName));
        validateBid(auction, bidder, bidCreationDto.getAmount());
        Bid bid = Bid.builder().amount(bidCreationDto.getAmount()).auction(auction).user(bidder).build();
        auction.setCurrentPrice(bidCreationDto.getAmount());
        bidRepository.save(bid);
        auctionRepository.save(auction);
        return EntityToDtoMapper.toDto(bid);
    }

    public Page<BidResponseDto> getBidHistory(Integer auctionId, Pageable pageable) {
        Page<Bid> bidHistory = bidRepository.findAllBidsByAuctionId(auctionId, pageable);
        return bidHistory.map(EntityToDtoMapper::toDto);
    }

    private void validateBid(Auction auction, User bidder, Double amount) {
        if (auction.getUser().getUsername().equals(bidder.getUsername())) {
            throw new IllegalArgumentException("Auction owner cannot bid on their own auction");
        }
        if (auction.getStatus() != AuctionStatus.LIVE) {
            throw new IllegalArgumentException("Auction is not live yet, please wait");
        }

        Double basePrice = auction.getCurrentPrice() != null ? auction.getCurrentPrice() : auction.getStartingPrice();
        Double minimumAllowedBid = basePrice + auction.getMinimumIncrement();

        if (amount < minimumAllowedBid) {
            throw new IllegalArgumentException("Bid must be at least " + minimumAllowedBid);
        }

    }
}
