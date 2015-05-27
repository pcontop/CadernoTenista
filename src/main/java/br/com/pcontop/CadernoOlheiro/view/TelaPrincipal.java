package br.com.pcontop.CadernoOlheiro.view;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 22/11/13
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public interface TelaPrincipal {
    public Telas getTela();
    public void setParams(Map<ParametroTela, Object> params);
}
