/**
 * Developer: Kadvin Date: 14-7-10 下午9:20
 */
package dnt.itsnow.backend.web.exception;

import org.springframework.http.HttpStatus;

/**
 * 登录时验证码未填或者出错
 */
public class CaptchaException extends WebClientSideException {

    public CaptchaException(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }
}
