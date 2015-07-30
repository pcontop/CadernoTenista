package br.com.pcontop.CadernoTenista.model.importer;

import br.com.pcontop.CadernoTenista.bean.Partida;

import java.io.InputStream;

/**
 * Created by PauloBruno on 25/01/14.
 */
public interface Importer {
    public Partida leiaPartida(InputStream inputStream) throws ImporterException;
}
