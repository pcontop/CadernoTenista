package br.com.pcontop.CadernoTenista.model.export.restSpring;

import android.content.Context;
import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.service.rest.operations.PartidaTransfOperacoesRest;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by PauloBruno on 05/07/2014.
 */
public class RestOperationFactory {
    private static PartidaTransfOperacoesRest partidaTransfOperacoesRest;

    public static PartidaTransfOperacoesRest getPartidaTransfOperations(Context context){
        if (partidaTransfOperacoesRest==null){
            RestTemplate restTemplate = getRestTemplate();
            final String string = context.getString(R.string.servidor_rest);
            partidaTransfOperacoesRest = new PartidaTransfOperacoesRest(restTemplate, string);
        }
        return partidaTransfOperacoesRest;
    }

    private static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        return restTemplate;
    }
}
