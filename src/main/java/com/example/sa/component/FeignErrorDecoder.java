package com.example.sa.component;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.sa.service.ExchangeService;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * https://www.appsdeveloperblog.com/feign-error-handling-with-errordecoder/
 *
 */
@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {
    
    @Override
    public Exception decode(String methodKey, Response response) {
    	log.error("We are in custom Feign error handler******************************");
       
        switch (response.status()){
            case 400:
                     log.error("Status code " + response.status() + ", methodKey = " + methodKey);
            case 404:
            {
            		log.error("Status code " + response.status() + ", methodKey = " + methodKey);
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Http not found exception."); 
            }
            default:
                return new Exception(response.reason());
        } 
    }
    
}
