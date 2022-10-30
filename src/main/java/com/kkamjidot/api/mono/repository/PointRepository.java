package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Point;
import com.kkamjidot.api.mono.domain.enumerate.PointType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointRepository extends JpaRepository<Point, Long> {
    @Query("select sum(p.poiValue) from Point p where p.poiType = 'BUY_GIFTICON' and p.user.id = :userId")
    Long totalGifticonPurchases(@Param("userId") Long userId);

    Point findFirstByUserIdOrderByPoiDatetimeDesc(Long userId);
}