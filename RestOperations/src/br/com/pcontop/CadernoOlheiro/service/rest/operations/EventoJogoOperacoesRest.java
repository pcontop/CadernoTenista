package br.com.pcontop.CadernoOlheiro.service.rest.operations;

import br.com.pcontop.CadernoOlheiro.bean.EventoJogo;
import br.com.pcontop.CadernoOlheiro.service.rest.uri.UriEventoJogo;
import org.springframework.web.client.RestOperations;

public class EventoJogoOperacoesRest extends AbstractOperacoesRest {

    public EventoJogoOperacoesRest(RestOperations restOperations, String ServerUri) {
        super(restOperations, ServerUri);
    }

    public EventoJogo save(EventoJogo eventoJogo){
		EventoJogo eventoJogoRest = restOperations.postForObject(ServerUri + UriEventoJogo.SAVE , getRequestEntity(eventoJogo), EventoJogo.class);
		return eventoJogoRest;
	}

}
