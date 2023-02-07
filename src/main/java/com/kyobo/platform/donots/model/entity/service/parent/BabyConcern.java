package com.kyobo.platform.donots.model.entity.service.parent;

import lombok.*;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor // Builder를 위한 생성자 (NoArgsConstructor를 protected로 선언하면 오류 발생)
@NoArgsConstructor
@Builder
@Data
@ToString
public class BabyConcern implements Serializable {

    @Id
    private String key;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BabyConcern that = (BabyConcern) o;
        return key.equals(that.key) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, name);
    }
}
