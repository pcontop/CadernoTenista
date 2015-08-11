package br.com.pcontop.CadernoTenista.service.rest.operations;

import br.com.pcontop.CadernoTenista.bean.PartidaTransfTenis;
import br.com.pcontop.CadernoTenista.service.rest.uri.UriPartidaTransfTenis;
import org.springframework.web.client.RestOperations;


public class PartidaTransfTenisOperacoesRest extends AbstractOperacoesRest {

    public PartidaTransfTenisOperacoesRest(RestOperations restOperations, String ServerUri) {
        super(restOperations, ServerUri);
    }

    public PartidaTransfTenis save(PartidaTransfTenis partida){
		PartidaTransfTenis partidaRest = restOperations.postForObject(ServerUri + UriPartidaTransfTenis.SAVE , getRequestEntity(partida), PartidaTransfTenis.class);
		return partidaRest;
	}

}
