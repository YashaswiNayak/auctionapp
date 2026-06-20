package com.yashaswi.auctionapp.controller;

import com.yashaswi.auctionapp.dto.bid.BidCreationDto;
import com.yashaswi.auctionapp.dto.bid.BidResponseDto;
import com.yashaswi.auctionapp.service.BidService;
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
@RequestMapping("/api/auction/{id}")
@RequiredArgsConstructor
public class BidController {
    private final BidService bidService;

    @PostMapping("/bid")
    public ResponseEntity<BidResponseDto> placeBid(@PathVariable("id") Integer auctionId, @AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid BidCreationDto bidCreationDto) {
        BidResponseDto bidResponseDto = bidService.placeBid(auctionId, bidCreationDto, userDetails.getUsername());
        return ResponseEntity.ok(bidResponseDto);
    }

    @GetMapping("/history")
    public ResponseEntity<Page<BidResponseDto>> getHistory(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Integer auctionId, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(bidService.getBidHistory(auctionId, pageable));
    }
}
