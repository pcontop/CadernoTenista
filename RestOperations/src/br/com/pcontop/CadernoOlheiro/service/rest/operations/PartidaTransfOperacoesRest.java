package br.com.pcontop.CadernoOlheiro.service.rest.operations;

import br.com.pcontop.CadernoOlheiro.bean.PartidaTransf;
import br.com.pcontop.CadernoOlheiro.service.rest.uri.UriPartidaTransf;
import org.springframework.web.client.RestOperations;


public class PartidaTransfOperacoesRest extends AbstractOperacoesRest {

    public PartidaTransfOperacoesRest(RestOperations restOperations, String ServerUri) {
        super(restOperations, ServerUri);
    }

    public PartidaTransf save(PartidaTransf partida){
		PartidaTransf partidaRest = restOperations.postForObject(ServerUri + UriPartidaTransf.SAVE , getRequestEntity(partida), PartidaTransf.class);
		return partidaRest;
	}

}
