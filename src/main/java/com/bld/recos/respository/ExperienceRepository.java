package com.bld.recos.respository;

import com.bld.recos.model.Experience;
import com.bld.recos.model.ExperienceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByCategory(ExperienceCategory attraction);
}
