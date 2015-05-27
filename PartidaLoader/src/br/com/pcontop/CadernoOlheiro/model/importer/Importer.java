package br.com.pcontop.CadernoOlheiro.model.importer;

import br.com.pcontop.CadernoOlheiro.bean.Partida;

import java.io.InputStream;

/**
 * Created by PauloBruno on 25/01/14.
 */
public interface Importer {
    public Partida leiaPartida(InputStream inputStream) throws ImporterException;
}
