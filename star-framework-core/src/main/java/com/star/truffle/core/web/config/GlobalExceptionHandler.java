/**create by liuhua at 2018年8月14日 下午5:16:13**/
package com.star.truffle.core.web.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.truffle.core.web.ErrorInfo;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger("errorLogger");

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    ResponseEntity<String> handleMaxUploadSizeExceededException(HttpServletRequest request, MaxUploadSizeExceededException e) {
        String msg = "Maximum upload size of " + e.getMaxUploadSize() + " bytes exceeded";
        return toJsonResponse(HttpStatus.PAYLOAD_TOO_LARGE, request, msg);
    }
    
    /*
     * Adding a JSON String manually to the response.
     *
     * Some services return a binary file or text/plain, etc. Then an ErrorInfo instance is manually converted
     * to JSON and written down in the response body.
     */
    private ResponseEntity<String> toJsonResponse(HttpStatus status, HttpServletRequest request, String exceptionMessage) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json");
        try {
            StringBuffer requestURL = (request == null) ? new StringBuffer("") : request.getRequestURL();
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(status)
                    .headers(responseHeaders)
                    .body(mapper.writeValueAsString(new ErrorInfo(status, requestURL, exceptionMessage)));
        } catch (JsonProcessingException e1) {
            logger.error("Could not process to JSON the given ErrorInfo instance", e1);
            return ResponseEntity.status(status).headers(responseHeaders).body("");
        }
    }
}