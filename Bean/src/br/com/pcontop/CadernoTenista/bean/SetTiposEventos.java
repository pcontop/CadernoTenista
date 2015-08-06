package br.com.pcontop.CadernoTenista.bean;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by PauloBruno on 22/06/2014.
 */
public class SetTiposEventos extends LinkedHashSet<TipoEvento> {
    public SetTiposEventos( ){};
    public SetTiposEventos(Set<TipoEvento> tipoEventos){
        this.addAll(tipoEventos);
    }
}
