package br.com.pcontop.CadernoOlheiro.service.rest.operations;

import br.com.pcontop.CadernoOlheiro.bean.Jogador;
import br.com.pcontop.CadernoOlheiro.service.rest.uri.UriJogador;
import org.springframework.web.client.RestOperations;

public class JogadorOperacoesRest extends AbstractOperacoesRest {

    public JogadorOperacoesRest(RestOperations restOperations, String ServerUri) {
        super(restOperations, ServerUri);
    }

    public Jogador save(Jogador jogador){
		Jogador jogadorRest = restOperations.postForObject(ServerUri + UriJogador.SAVE , getRequestEntity(jogador), Jogador.class);
		return jogadorRest;
	}

}
