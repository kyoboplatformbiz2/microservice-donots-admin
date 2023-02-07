package com.kyobo.platform.donots.model.entity.service.parent;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "baby")
@TypeDef(name = "json", typeClass = JsonType.class)
//@JsonFilter("BabyFilter")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // Builder를 위한 생성자 (NoArgsConstructor를 protected로 선언하면 오류 발생)
@Builder
@Data
@ToString(exclude = {"parent"}, callSuper = true)  // ToString 순환참조 방지
public class Baby {

    @Id @GeneratedValue
    @Column(name = "baby_key")
    private Long key;

    /**
     * @JsonBackReference
     * - 연관관계의 주인 Entity 에 선언
     * - 직렬화가 되지 않도록 수행
     * 다음 Exception 발생:
     * org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: could not extract ResultSet; nested exception is com.fasterxml.jackson.databind.JsonMappingException: could not extract ResultSet (through reference chain: com.kyobo.platform.retrieve.bizModule.parent.entity.Parent[&quot;babies&quot;]-&gt;org.hibernate.collection.internal.PersistentBag[0]-&gt;com.kyobo.platform.retrieve.bizModule.parent.entity.Baby[&quot;babyAllergens&quot;])
     * TODO 순환참조 방지를 위한 직렬화 제외. 직렬화가 필요한 경우가 있으면 다른 방법 선택(@JsonManagedReference 등)
     *
     * @JsonManagedReference
     * - 연관관계 주인 반대 Entity 에 선언
     * - 정상적으로 직렬화 수행
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "parent_key")
    @JsonBackReference // TODO Member 프로젝트와 다른 부분
    private Parent parent;

    private String nickname;
    private LocalDate birthdate;
    private String height;
    private String weight;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String profilePictureUrl;
    private Integer profilePictureThumbnailOrder;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    @Builder.Default
    private List<BabyAllergyIngredient> allergyIngredients = new ArrayList<>();

    @Type(type = "json")
    @Column(columnDefinition = "json")
    @Builder.Default
    private List<BabyConcern> concerns = new ArrayList<>();

    public boolean isProfileSelected() {
        return key.equals(parent.getProfileSelectedBabyKey());
    }

    // 연관관계 편의 메서드
    public void connectParent(Parent parent) {
        this.parent = parent;
        parent.getBabies().add((this));
    }

//    public void removeBaby(Long babyKey) {
//
//        for (Baby babyIter : parent.getBabies()) {
//            if (babyIter.getKey() == babyKey)
//                parent.getBabies().remove(babyIter);
//        }
//
//        // TODO 여기 오류
//        this = null;
//    }

    // TODO API 별로 유효한 값만 받도록 메서드를 여러개 둬야할까?
    // 아니면 유효성 체크로 다 걸러낼까?
    public void deepCopyAllExceptKeyFrom(Baby baby) {
        // 값이 없는 필드가 변경되는 것은 의도에 맞지 않으므로 값이 있는 필드만 복사한다
        // TODO Later Reflection 사용한 동적 null 검사
        if (baby.getParent() != null)   this.parent = baby.getParent(); // TODO BabyDto.toEntity()에서 ParentKey가 없는 경우 null로 셋팅을 해주긴 하지만, 여기도 자체적인 유효성 검사는 필요할 듯
        if (baby.getNickname() != null)	this.nickname = baby.getNickname();
        if (baby.getBirthdate() != null)	this.birthdate = baby.getBirthdate();
        if (baby.getHeight() != null)	this.height = baby.getHeight();
        if (baby.getWeight() != null)	this.weight = baby.getWeight();
        if (baby.getGender() != null)	this.gender = baby.getGender();
        if (baby.getProfilePictureUrl() != null)	this.profilePictureUrl = baby.getProfilePictureUrl();
        if (baby.getProfilePictureThumbnailOrder() != null)	this.profilePictureThumbnailOrder = baby.getProfilePictureThumbnailOrder();
        if (!CollectionUtils.isEmpty(baby.getAllergyIngredients()))	this.allergyIngredients = baby.getAllergyIngredients();
        if (!CollectionUtils.isEmpty(baby.getConcerns()))	this.concerns = baby.getConcerns();
    }
}
