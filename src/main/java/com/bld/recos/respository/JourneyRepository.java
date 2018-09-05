package com.bld.recos.respository;

import com.bld.recos.model.Journey;
import com.bld.recos.model.JourneyType;
import com.bld.recos.model.TimelineEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long> {

    List<Journey> findByCategory(JourneyType category);

    List<Journey> findByFromIdAndToId(Long fromId, Long toId);
}
