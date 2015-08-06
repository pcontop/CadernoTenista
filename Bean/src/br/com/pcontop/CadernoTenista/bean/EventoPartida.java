package br.com.pcontop.CadernoTenista.bean;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 28/10/13
 * time: 22:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class EventoPartida implements Comparable<EventoPartida>, Serializable {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;
    private Date hora;

    private EventoPartida() {

    }

    public static Builder create(){
        return new Builder();
    }

    @Override
    public int compareTo(EventoPartida o) {
        return hora.compareTo(o.hora);
    }

    public static class Builder {
        EventoPartida eventoPartida = new EventoPartida();

        public Builder setTipoEvento(TipoEvento tipoEvento) {
            eventoPartida.tipoEvento = tipoEvento;
            return this;
        }

        public Builder setHora(Date hora) {
            eventoPartida.hora = hora;
            return this;
        }

        public EventoPartida commit(){
            eventoPartida.id=UUIDProvider.getNew();
            return eventoPartida;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventoPartida that = (EventoPartida) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    private int hashCodeCalc(int result, Object o){
        if (o !=null){
            return 31 * result + o.hashCode();
        } else {
            return result;
        }
    }

    @Override
    public int hashCode() {
        int result=17;
        result = hashCodeCalc(result, id);
        return result;
    }

    @Override
    public String toString() {
        String result = "EventoPartida: ["
                + "id: " + id
                + ", Tipo Evento: " + tipoEvento
                + ", Hora: " + hora
                +"]";
        return result;
    }

    public String getId() {
        return id;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public Date getHora() {
        return hora;
    }

}
