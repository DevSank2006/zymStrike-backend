package com.sanket.Zyvix.Repository;

import com.sanket.Zyvix.Entities.SuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionsRepository extends JpaRepository<SuggestionEntity,Long> {
    public SuggestionEntity findByText(String text);
}
