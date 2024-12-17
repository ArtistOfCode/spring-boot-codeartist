package com.codeartist.component.core.entity.enums;

import com.codeartist.component.core.SpringContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 环境配置
 *
 * @author AiJiangnan
 * @date 2022/7/15
 */
@AllArgsConstructor
public enum Environments {

    JUNIT(Profiles.JUNIT, "global.environment.junit"),
    LOCAL(Profiles.LOCAL, "global.environment.local"),
    TEST(Profiles.TEST, "global.environment.test"),
    PROD(Profiles.PROD, "global.environment.prod"),
    ;

    @Getter
    private final String profile;
    private final String name;

    public String getName() {
        return SpringContext.getMessage(this.name);
    }

    public boolean is() {
        return SpringContext.acceptsProfiles(this.getProfile());
    }

    public boolean not() {
        return !is();
    }

    public interface Profiles {

        String JUNIT = "junit";
        String LOCAL = "local";
        String TEST = "test";
        String PROD = "prod";
        String NOT_PROD = "!prod";
    }
}
