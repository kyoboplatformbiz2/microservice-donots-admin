package com.kyobo.platform.donots.model.entity.service.parent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ActivityIndicator {
    private int recipePostingCount;
    private int scrapbookedCount;
    private int reactionAddingCount;
    private int scrapbookingCount;
}
