package com.kyobo.platform.donots.model.entity.service.parent;

public enum ParentGrade {
    /**
     * ParentGrade#getNextGrade(ParentGrade)에서 ordinal()을 사용하므로 ParentGrade 인스턴스의 선언순서는 오름차순으로 정렬되어있어야 한다
     */
    LV1("LV1",
            "글레이즈도낫",
            "http://test.com/test",
            new ActivityIndicator(0, 0, 0, 0)
    ),
    LV2("LV2",
            "초코도낫",
            "http://test.com/test",
            new ActivityIndicator(0, 0, 10, 15)
    ),
    LV3("LV3",
            "스프링클도낫",
            "http://test.com/test",
            new ActivityIndicator(15, 50, 0, 0)
    );

    public final String key;
    public final String name;
    public final String badgeImgUrl;
    public final ActivityIndicator promotionCriteria;

    ParentGrade(String key, String name, String badgeImgUrl, ActivityIndicator promotionCriteria) {
        this.key = key;
        this.name = name;
        this.badgeImgUrl = badgeImgUrl;
        this.promotionCriteria = promotionCriteria;
    }

    /**
     * Key로 ParentGrade를 찾아 객체를 받는다.
     *
     * @return key와 맵핑된 ParentGrade, 없으면 null을 반환한다.
     */
    public static ParentGrade findGradeByKey(String key) {
        for (ParentGrade pg : ParentGrade.values()) {
            if (key.equals(pg.key))
                return pg;
        }

        return null;
    }

    public static ParentGrade getNextGrade(ParentGrade pg) {
        for (ParentGrade tempPg : ParentGrade.values()) {
            if (tempPg.ordinal() == pg.ordinal() + 1)
                return tempPg;
        }

        return null;
    }

    public static ParentGrade calculateSatisfiedGrade(String currentGradeKey, ActivityIndicator activityIndicator) {
        return calculateSatisfiedGrade(
                currentGradeKey,
                activityIndicator.getRecipePostingCount(),
                activityIndicator.getScrapbookedCount(),
                activityIndicator.getReactionAddingCount(),
                activityIndicator.getScrapbookingCount()
        );
    }

    public static ParentGrade calculateSatisfiedGrade(String currentGradeKey, int recipePostingCount, int scrapbookedCount, int reactionAddingCount, int scrapbookingCount) {
        ParentGrade currentGrade = findGradeByKey(currentGradeKey);

        switch (currentGrade) {
            case LV1:
                if (isSatisfiedLv3(recipePostingCount, scrapbookedCount))
                    return ParentGrade.LV3;
                else if (isSatisfiedLv2(reactionAddingCount, scrapbookingCount))
                    return ParentGrade.LV2;
                else
                    return currentGrade;

            case LV2:
                if (isSatisfiedLv3(recipePostingCount, scrapbookedCount))
                    return ParentGrade.LV3;
                else
                    return currentGrade;

            default:
                return currentGrade;
        }
    }

    private static boolean isSatisfiedLv3(int recipePostingCount, int scrapbookedCount) {
        if (recipePostingCount >= ParentGrade.LV3.promotionCriteria.getRecipePostingCount() &&
                scrapbookedCount >= ParentGrade.LV3.promotionCriteria.getScrapbookedCount()) {
            return true;
        }

        return false;
    }

    private static boolean isSatisfiedLv2(int reactionAddingCount, int scrapbookingCount) {
        if (reactionAddingCount >= ParentGrade.LV2.promotionCriteria.getReactionAddingCount() &&
                scrapbookingCount >= ParentGrade.LV2.promotionCriteria.getScrapbookingCount()) {
            return true;
        }

        return false;
    }

    public static boolean hasAuthorityToUseSocialMediaUrlField(ParentGrade pg) {
        switch (pg) {
            case LV1:
                return false;
            case LV2:
                return false;
            case LV3:
                return true;
            default:
                return false;
        }
    }
}
