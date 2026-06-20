package com.yashaswi.auctionapp.entity;

import com.yashaswi.auctionapp.enums.AuctionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auction")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "title", nullable = false)
    @NotBlank
    String title;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "starting_price", nullable = false)
    @NotNull
    Double startingPrice;

    @Column(name = "current_price")
    Double currentPrice;

    @Column(name = "minimum_increment", nullable = false)
    Double minimumIncrement;

    @Column(name = "start_time", nullable = false)
    @NotNull
    LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    @NotNull
    LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    AuctionStatus status = AuctionStatus.DRAFT;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Bid> bids = new ArrayList<>();


}

