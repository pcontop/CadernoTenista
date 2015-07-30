package br.com.pcontop.CadernoTenista.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

/**
 * Created by pcont_000 on 25/07/2015.
 */
public class TempoPartida implements Serializable{
    @Id
    private String id;
    private Date dataInicio;
    private Date dataFim;
    private TiposTempoPartida tipoTempoPartida;

    public String getId() {
        return id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public TiposTempoPartida getTipoTempoPartida() {
        return tipoTempoPartida;
    }

    public void setTipoTempoPartida(TiposTempoPartida tipoTempoPartida) {
        this.tipoTempoPartida = tipoTempoPartida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TempoPartida that = (TempoPartida) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "TempoPartida{" +
                "id='" + id + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", tipoTempoPartida=" + tipoTempoPartida +
                '}';
    }

    private TempoPartida(){
    }

    public static Builder create(){
        return new Builder();
    }

    public static class Builder {
        private TempoPartida tempoPartida= new TempoPartida();

        public Builder setDataInicio(Date dataInicio){
            tempoPartida.dataInicio = dataInicio;
            return this;
        }

        public Builder setDataFim(Date dataFim){
            tempoPartida.dataFim = dataFim;
            return this;
        }

        public Builder setTipo(TiposTempoPartida tipoTempoPartida){
            tempoPartida.tipoTempoPartida = tipoTempoPartida;
            return this;
        }

        public TempoPartida commit(){
            if (tempoPartida.id==null || tempoPartida.tipoTempoPartida==null){
                throw new UnsupportedOperationException();
            }
            tempoPartida.id=UUIDProvider.getNew();
            return tempoPartida;
        }

    }
}
