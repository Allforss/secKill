package com.alice.pet.base.web;

import com.alice.pet.base.common.exception.BizException;
import com.alice.pet.base.model.MessageBean;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xudong.cheng
 * @date 2018/1/17 下午1:25
 */
@Slf4j
public abstract class BaseController {

    @FunctionalInterface
    public interface ThrowSupplier<T> {
        T get() throws Exception;
    }

    public <T> MessageBean process(ThrowSupplier<T> supplier) {
        try {
            return new MessageBean(supplier.get());
        } catch (BizException e) {
            return new MessageBean(e);
        } catch (Exception e) {
            log.error("System Error", e);
            return new MessageBean(e);
        }
    }

}
