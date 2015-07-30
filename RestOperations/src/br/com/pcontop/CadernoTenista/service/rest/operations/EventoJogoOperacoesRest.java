package br.com.pcontop.CadernoTenista.service.rest.operations;

import br.com.pcontop.CadernoTenista.bean.EventoPartida;
import br.com.pcontop.CadernoTenista.service.rest.uri.UriEventoJogo;
import org.springframework.web.client.RestOperations;

public class EventoJogoOperacoesRest extends AbstractOperacoesRest {

    public EventoJogoOperacoesRest(RestOperations restOperations, String ServerUri) {
        super(restOperations, ServerUri);
    }

    public EventoPartida save(EventoPartida eventoPartida){
		EventoPartida eventoPartidaRest = restOperations.postForObject(ServerUri + UriEventoJogo.SAVE , getRequestEntity(eventoPartida), EventoPartida.class);
		return eventoPartidaRest;
	}

}
