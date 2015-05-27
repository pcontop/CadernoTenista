package br.com.pcontop.CadernoOlheiro.service.rest.operations;

import br.com.pcontop.CadernoOlheiro.bean.DummyJar;
import br.com.pcontop.CadernoOlheiro.service.rest.uri.UriDummy;
import org.springframework.web.client.RestOperations;

public class DummyOperacoesRest  extends AbstractOperacoesRest{

    public DummyOperacoesRest(RestOperations restOperations, String ServerUri) {
        super(restOperations, ServerUri);
    }


    public DummyJar create(DummyJar dummy){
		DummyJar dummyRest = restOperations.postForObject(ServerUri + UriDummy.CREATE , getRequestEntity(dummy), DummyJar.class);
		return dummyRest;
	}

}
