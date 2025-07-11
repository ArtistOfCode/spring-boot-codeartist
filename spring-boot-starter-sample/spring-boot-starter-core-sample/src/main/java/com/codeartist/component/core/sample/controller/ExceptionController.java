package com.codeartist.component.core.sample.controller;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.exception.BadRequestException;
import com.codeartist.component.core.exception.BusinessException;
import com.codeartist.component.core.sample.entity.param.UserParam;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

/**
 * 异常接口测试
 *
 * @author AiJiangnan
 * @date 2022/7/22
 */
@RestController
@RequestMapping("/api/exception")
public class ExceptionController {

    @PostMapping("/client")
    public void client(@RequestParam(value = "type", defaultValue = "0") int type,
                       @RequestBody UserParam param) throws BindException {
        switch (type) {
            case 0:
                BindException bindException = new BindException(new UserParam(), "user");
                bindException.reject("user.null");
                bindException.rejectValue("name", "name.null");
                throw bindException;
            case 1:
                SpringContext.validate(param);
                break;
            case 2:
                Errors errors = new BeanPropertyBindingResult(new UserParam(), "user");
                errors.reject("user.null");
                errors.rejectValue("name", "name.null");
                throw new BadRequestException(GlobalErrorCode.GLOBAL_CLIENT_ERROR, errors);
            default:
                throw new BadRequestException(GlobalErrorCode.GLOBAL_CLIENT_ERROR);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @PostMapping("/business")
    public void business(@RequestParam(value = "type", defaultValue = "0") int type,
                         @RequestBody UserParam param) {
        switch (type) {
            case 0:
                Assert.isTrue(false, "业务异常");
                break;
            case 1:
                Errors errors = new BeanPropertyBindingResult(new UserParam(), "user");
                errors.reject("user.null");
                errors.rejectValue("name", "name.null");
                throw new BusinessException(GlobalErrorCode.GLOBAL_BUSINESS_ERROR, errors);
            case 2:
                throw new RuntimeException("服务器异常");
            default:
                throw new BusinessException(GlobalErrorCode.GLOBAL_BUSINESS_ERROR);
        }
    }
}
