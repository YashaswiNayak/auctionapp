package com.yashaswi.auctionapp.controller;

import com.yashaswi.auctionapp.dto.auction.AuctionCreationDto;
import com.yashaswi.auctionapp.dto.auction.AuctionResponseDto;
import com.yashaswi.auctionapp.dto.auction.AuctionUpdateDto;
import com.yashaswi.auctionapp.service.AuctionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auction")
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;

    @GetMapping
    public ResponseEntity<Page<AuctionResponseDto>> getAllAuctionsByUser(@AuthenticationPrincipal UserDetails userDetails, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<AuctionResponseDto> auctionResponseDto = auctionService.getAllAuctionByUser(userDetails.getUsername(), pageable);
        return ResponseEntity.ok(auctionResponseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AuctionResponseDto>> getAllAuctions(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<AuctionResponseDto> auctions = auctionService.getAuctions(pageable);
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionResponseDto> getAuction(@PathVariable Integer id) {
        return ResponseEntity.ok(auctionService.getAuction(id));
    }

    @PostMapping
    public ResponseEntity<AuctionResponseDto> createAuction(@RequestBody @Valid AuctionCreationDto auctionCreationDto, @AuthenticationPrincipal UserDetails userDetails) {
        AuctionResponseDto auctionResponseDto = auctionService.createNewAuction(auctionCreationDto, userDetails.getUsername());

        return ResponseEntity.ok(auctionResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuctionResponseDto> updateAuction(@RequestBody @Valid AuctionUpdateDto auctionUpdateDto, @AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id) {
        AuctionResponseDto auctionResponseDto = auctionService.updateAuction(userDetails.getUsername(), id, auctionUpdateDto);

        return ResponseEntity.ok(auctionResponseDto);
    }

    @PostMapping("/{id}/schedule")
    public ResponseEntity<AuctionResponseDto> scheduleAuction(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        AuctionResponseDto auctionResponseDto = auctionService.finalizeAuction(userDetails.getUsername(), id);
        return ResponseEntity.ok(auctionResponseDto);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<AuctionResponseDto> cancelAuction(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        AuctionResponseDto auctionResponseDto = auctionService.cancelAuction(userDetails.getUsername(), id);
        return ResponseEntity.ok(auctionResponseDto);
    }

    @DeleteMapping("/{id}")
    public String deleteAuction(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id) {
        return auctionService.deleteAuction(userDetails.getUsername(), id);
    }


}
