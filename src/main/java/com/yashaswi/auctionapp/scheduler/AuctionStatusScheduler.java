package com.yashaswi.auctionapp.scheduler;

import com.yashaswi.auctionapp.entity.Auction;
import com.yashaswi.auctionapp.enums.AuctionStatus;
import com.yashaswi.auctionapp.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuctionStatusScheduler {
    private final AuctionRepository auctionRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateAuctionStatuses() {
        LocalDateTime now = LocalDateTime.now();

        List<Auction> auctionsToActivate = auctionRepository.findByStatusAndStartTimeBeforeAndEndTimeAfter(AuctionStatus.SCHEDULED, now, now);

        for (Auction auction : auctionsToActivate) {
            auction.setStatus(AuctionStatus.LIVE);
        }

        List<Auction> auctionsToEnd = auctionRepository.findByStatusAndEndTimeBefore(AuctionStatus.LIVE, now);

        for (Auction auction : auctionsToEnd) {
            auction.setStatus(AuctionStatus.FINISH);
        }


    }

}
