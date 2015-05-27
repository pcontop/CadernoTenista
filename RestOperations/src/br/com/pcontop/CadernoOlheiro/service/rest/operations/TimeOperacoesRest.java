package br.com.pcontop.CadernoOlheiro.service.rest.operations;

import br.com.pcontop.CadernoOlheiro.bean.Time;
import br.com.pcontop.CadernoOlheiro.service.rest.uri.UriTime;
import org.springframework.web.client.RestOperations;

public class TimeOperacoesRest extends AbstractOperacoesRest {

    public TimeOperacoesRest(RestOperations restOperations, String ServerUri) {
        super(restOperations, ServerUri);
    }

    public Time save(Time time){
		Time timeRest = restOperations.postForObject(ServerUri + UriTime.SAVE , getRequestEntity(time), Time.class);
		return timeRest;
	}

}
