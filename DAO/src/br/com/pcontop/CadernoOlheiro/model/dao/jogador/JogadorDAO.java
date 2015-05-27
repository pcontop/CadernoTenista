package br.com.pcontop.CadernoOlheiro.model.dao.jogador;

import br.com.pcontop.CadernoOlheiro.bean.Jogador;

import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 09/11/13
 * time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public interface JogadorDAO {

    public Jogador get(String uuid);
    public void remova(Jogador jogador);

}
