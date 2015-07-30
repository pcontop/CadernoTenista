package br.com.pcontop.CadernoTenista.service.rest.operations;

import br.com.pcontop.CadernoTenista.bean.Localidade;
import br.com.pcontop.CadernoTenista.service.rest.uri.UriLocalidade;
import org.springframework.web.client.RestOperations;

public class LocalidadeOperacoesRest extends AbstractOperacoesRest {


    public LocalidadeOperacoesRest(RestOperations restOperations, String ServerUri) {
        super(restOperations, ServerUri);
    }

    public Localidade save(Localidade localidade){
		Localidade localidadeRest = restOperations.postForObject(ServerUri + UriLocalidade.SAVE , getRequestEntity(localidade), Localidade.class);
		return localidadeRest;
	}

}