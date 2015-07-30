package br.com.pcontop.CadernoTenista.service.rest.operations;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestOperations;

import java.util.Arrays;

/**
 * Created by PauloBruno on 05/07/2014.
 */
public abstract class AbstractOperacoesRest {
    protected final String ServerUri;
    protected final RestOperations restOperations;

    public AbstractOperacoesRest(RestOperations restOperations, String ServerUri){
        this.restOperations = restOperations;
        this.ServerUri = ServerUri;
    }

    protected HttpHeaders getHeaders(String auth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
        headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

        return headers;
    }

    protected <T> HttpEntity<T> getRequestEntity(T object){
        HttpEntity<T> requestEntity = new HttpEntity<>(
                object,
                getHeaders("letsnosh" + ":" + "noshing"));

        return requestEntity;

    }
}
