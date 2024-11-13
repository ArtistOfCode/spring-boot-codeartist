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

    JUNIT(ProfileConst.JUNIT_PROFILE, "global.environment.junit"),
    LOCAL(ProfileConst.LOCAL_PROFILE, "global.environment.local"),
    TEST(ProfileConst.TEST_PROFILE, "global.environment.test"),
    PROD(ProfileConst.PROD_PROFILE, "global.environment.prod"),
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

    public interface ProfileConst {

        String JUNIT_PROFILE = "junit";
        String LOCAL_PROFILE = "local";
        String TEST_PROFILE = "test";
        String PROD_PROFILE = "prod";
        String NOT_PROD_PROFILE = "!prod";
    }
}
