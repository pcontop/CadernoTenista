package br.com.pcontop.CadernoOlheiro.bean;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by PauloBruno on 22/06/2014.
 */
public class SetTiposEventos extends LinkedHashSet<TiposEvento> {
    public SetTiposEventos( ){};
    public SetTiposEventos(Set<TiposEvento> tiposEventos){
        this.addAll(tiposEventos);
    }
}
