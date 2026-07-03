package com.yashaswi.auctionapp.service;

import com.yashaswi.auctionapp.dto.auction.AuctionCreationDto;
import com.yashaswi.auctionapp.dto.auction.AuctionResponseDto;
import com.yashaswi.auctionapp.dto.auction.AuctionUpdateDto;
import com.yashaswi.auctionapp.entity.Auction;
import com.yashaswi.auctionapp.entity.User;
import com.yashaswi.auctionapp.enums.AuctionStatus;
import com.yashaswi.auctionapp.exception.AuctionNotFoundException;
import com.yashaswi.auctionapp.exception.InvalidAuctionStateException;
import com.yashaswi.auctionapp.exception.UserNotFoundException;
import com.yashaswi.auctionapp.mapper.EntityToDtoMapper;
import com.yashaswi.auctionapp.repository.AuctionRepository;
import com.yashaswi.auctionapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    public AuctionResponseDto createNewAuction(AuctionCreationDto auctionCreationDto, String username) {
        User creator = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User does not exist"));
        Auction auction = Auction.builder().title(auctionCreationDto.getTitle()).description(auctionCreationDto.getDescription()).startingPrice(auctionCreationDto.getStartingPrice()).currentPrice(auctionCreationDto.getStartingPrice()).minimumIncrement(auctionCreationDto.getMinimumIncrement()).startTime(auctionCreationDto.getStartTime()).endTime(auctionCreationDto.getEndTime()).user(creator).build();
        log.debug(auction.getTitle());
        auctionRepository.save(auction);
        return EntityToDtoMapper.toDto(auction);
    }

    public Page<AuctionResponseDto> getAllAuctionByUser(String username, Pageable pageable) {
        Page<Auction> auctionLists = auctionRepository.findAllByUserUsernameWithUser(username, pageable);
        log.info("Returning all auctions");
        return auctionLists.map(EntityToDtoMapper::toDto);
    }

    public Page<AuctionResponseDto> getAuctions(Pageable pageable) {
        Page<Auction> auctions = auctionRepository.findAll(pageable);
        return auctions.map(EntityToDtoMapper::toDto);
    }

    public AuctionResponseDto getAuctionById(String username, Integer id) {
        Auction auction = auctionRepository.findByIdAndUser_Username(id, username).orElseThrow(() -> new AuctionNotFoundException("Auction Not Found"));
        return EntityToDtoMapper.toDto(auction);
    }

    public AuctionResponseDto updateAuction(String username, Integer id, AuctionUpdateDto auctionUpdateDto) {
        Auction auction = auctionRepository.findByIdAndUser_Username(id, username).orElseThrow(() -> new AuctionNotFoundException("Auction Not Found"));
        if (auction.getStatus() != AuctionStatus.DRAFT) {
            throw new InvalidAuctionStateException("Cannot update an auction that is not in DRAFT stage");
        }
        if (auctionUpdateDto.getTitle() != null) {
            auction.setTitle(auctionUpdateDto.getTitle());
        }
        if (auctionUpdateDto.getDescription() != null) {
            auction.setDescription(auctionUpdateDto.getDescription());
        }
        if (auctionUpdateDto.getMinimumIncrement() != null) {
            auction.setMinimumIncrement(auctionUpdateDto.getMinimumIncrement());
        }
        if (auctionUpdateDto.getStartTime() != null) {
            auction.setStartTime(auctionUpdateDto.getStartTime());
        }
        if (auctionUpdateDto.getEndTime() != null) {
            auction.setEndTime(auctionUpdateDto.getEndTime());
        }
        auctionRepository.save(auction);
        log.info("Updated Successfully");
        return EntityToDtoMapper.toDto(auction);
    }

    public String deleteAuction(String username, Integer id) {
        Auction auction = auctionRepository.findByIdAndUser_Username(id, username).orElseThrow(() -> new AuctionNotFoundException("Auction Not Found"));
        if (auction.getStatus() != AuctionStatus.DRAFT) {
            throw new InvalidAuctionStateException("Cannot update an auction that is not in DRAFT stage");
        }
        auctionRepository.delete(auction);
        log.info("Deleted Successfully");
        return "Deleted Successfully";
    }


}
