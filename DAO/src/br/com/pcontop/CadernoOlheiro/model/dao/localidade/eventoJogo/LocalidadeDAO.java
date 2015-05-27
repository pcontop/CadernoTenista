package br.com.pcontop.CadernoOlheiro.model.dao.localidade.eventoJogo;

import br.com.pcontop.CadernoOlheiro.bean.Localidade;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 09/11/13
 * time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public interface LocalidadeDAO {

    public Localidade get(String id);
    public Localidade getPorDescricao(String descricao);
    public boolean insiraOuAtualize(Localidade localidade);
    public void remova(Localidade localidade);

}
