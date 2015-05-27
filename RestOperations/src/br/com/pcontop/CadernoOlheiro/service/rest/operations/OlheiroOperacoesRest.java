package br.com.pcontop.CadernoOlheiro.service.rest.operations;

import br.com.pcontop.CadernoOlheiro.bean.Olheiro;
import br.com.pcontop.CadernoOlheiro.service.rest.uri.UriOlheiro;
import org.springframework.web.client.RestOperations;

public class OlheiroOperacoesRest extends AbstractOperacoesRest {

    public OlheiroOperacoesRest(RestOperations restOperations, String ServerUri) {
        super(restOperations, ServerUri);
    }

    public Olheiro save(Olheiro olheiro){
		Olheiro olheiroRest = restOperations.postForObject(ServerUri + UriOlheiro.SAVE , getRequestEntity(olheiro), Olheiro.class);
		return olheiroRest;
	}

}
