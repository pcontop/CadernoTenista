package br.com.pcontop.CadernoTenista.model.export.restSpring;

import android.content.Context;
import android.util.Log;
import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.bean.PartidaTransfTenis;
import br.com.pcontop.CadernoTenista.model.export.Exporter;
import br.com.pcontop.CadernoTenista.model.export.ExporterException;
import br.com.pcontop.CadernoTenista.service.rest.operations.PartidaTransfTenisOperacoesRest;

/**
 * Created by PauloBruno on 21/06/2014.
 */
public class RestExporter implements Exporter {

    @Override
    public boolean export(Context context, Partida partida) throws ExporterException {
        final PartidaTransfTenisOperacoesRest partidaTransfOperacoes = RestOperationFactory.getPartidaTransfOperations(context);
        //Cria objeto DTO.
        final PartidaTransfTenis partidaTransf = new PartidaTransfTenis(partida);
        try {
            PartidaTransfTenis partidaRest = partidaTransfOperacoes.save(partidaTransf);
            if (partidaRest!=null){
                partida.setUltimoEnvio(partidaRest.getUltimoEnvio());
                return true;
            }
        } catch (Exception e) {
            Log.e("RestExporter", "Erro ao exportar partida " + partidaTransf, e);
        }
        return false;

    }


}
