package com.yashaswi.auctionapp.repository;

import com.yashaswi.auctionapp.entity.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BidRepository extends JpaRepository<Bid, Integer> {
    @Query(value = """
            select b from Bid b
            join fetch b.user
            join fetch b.auction
            where b.auction.id = :auctionId
            order by b.createdAt DESC
            """,
            countQuery = """
                        SELECT COUNT(b) FROM Bid b
                        WHERE b.auction.id = :auctionId
                    """)
    Page<Bid> findAllBidsByAuctionId(@Param("auctionId") Integer auctionId, Pageable pageable);
}
