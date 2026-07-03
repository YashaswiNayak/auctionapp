package com.yashaswi.auctionapp.repository;

import com.yashaswi.auctionapp.entity.Auction;
import com.yashaswi.auctionapp.enums.AuctionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {

    @Query(value = """
                select a
                from Auction a
                join fetch a.user
                where a.user.username = :username
            """, countQuery = """
                select count(a)
                from Auction a
                where a.user.username = :username
            """)
    Page<Auction> findAllByUserUsernameWithUser(@Param("username") String username, Pageable pageable);

    @Query("""
                select a
                from Auction a
                join fetch a.user
                where a.id = :id and a.user.username = :username
            """)
    Optional<Auction> findByIdAndUser_Username(@Param("id") Integer id, @Param("username") String username);

    List<Auction> findByStatusAndStartTimeBeforeAndEndTimeAfter(
            AuctionStatus status,
            LocalDateTime now1,
            LocalDateTime now2
    );

    List<Auction> findByStatusAndEndTimeBefore(
            AuctionStatus status,
            LocalDateTime now
    );
}
