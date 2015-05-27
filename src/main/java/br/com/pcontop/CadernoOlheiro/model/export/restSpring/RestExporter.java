package br.com.pcontop.CadernoOlheiro.model.export.restSpring;

import android.content.Context;
import android.util.Log;
import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.bean.PartidaTransf;
import br.com.pcontop.CadernoOlheiro.model.export.Exporter;
import br.com.pcontop.CadernoOlheiro.model.export.ExporterException;
import br.com.pcontop.CadernoOlheiro.service.rest.operations.PartidaTransfOperacoesRest;

/**
 * Created by PauloBruno on 21/06/2014.
 */
public class RestExporter implements Exporter {

    @Override
    public boolean export(Context context, Partida partida) throws ExporterException {
        final PartidaTransfOperacoesRest partidaTransfOperacoes = RestOperationFactory.getPartidaTransfOperations(context);
        //Cria objeto DTO.
        final PartidaTransf partidaTransf = new PartidaTransf(partida);
        try {
            PartidaTransf partidaRest = partidaTransfOperacoes.save(partidaTransf);
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
