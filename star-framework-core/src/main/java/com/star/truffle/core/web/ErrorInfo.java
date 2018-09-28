/**create by liuhua at 2018年8月14日 下午5:14:48**/
package com.star.truffle.core.web;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorInfo {
    public final int code;
    public final String reason;
    public final String url;
    public String[] messages = null;

    public ErrorInfo(HttpStatus status, StringBuffer url, String... messages) {
        this.code = status.value();
        this.reason = status.name();
        this.url = url.toString();
        if (messages != null && messages.length > 0) {
            this.messages = messages;
        }
    }
}
