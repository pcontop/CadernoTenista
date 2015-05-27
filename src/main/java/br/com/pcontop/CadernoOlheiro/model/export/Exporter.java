package br.com.pcontop.CadernoOlheiro.model.export;

import android.content.Context;
import br.com.pcontop.CadernoOlheiro.bean.Partida;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 27/11/13
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */
public interface Exporter {
    public boolean export(Context context, Partida partida) throws ExporterException;
}
