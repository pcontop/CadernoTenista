package br.com.pcontop.CadernoTenista.model.export.restSpring;

import android.content.Context;
import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.service.rest.operations.PartidaTransfTenisOperacoesRest;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by PauloBruno on 05/07/2014.
 */
public class RestOperationFactory {
    private static PartidaTransfTenisOperacoesRest partidaTransfTenistaOperacoesRest;

    public static PartidaTransfTenisOperacoesRest getPartidaTransfOperations(Context context){
        if (partidaTransfTenistaOperacoesRest ==null){
            RestTemplate restTemplate = getRestTemplate();
            final String string = context.getString(R.string.servidor_rest);
            partidaTransfTenistaOperacoesRest = new PartidaTransfTenisOperacoesRest(restTemplate, string);
        }
        return partidaTransfTenistaOperacoesRest;
    }

    private static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        return restTemplate;
    }
}
