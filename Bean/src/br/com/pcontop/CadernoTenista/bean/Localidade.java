package br.com.pcontop.CadernoTenista.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 11/12/13
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Localidade implements Serializable {
    @Id
    private String id;
    private String descricao;
    private Double latitude;
    private Double longitude;

    public Localidade(){

    }

    private Localidade(String id, String descricao, Double latitude, Double longitude){
        this.id = id;
        this.descricao=descricao;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public static Builder create(){
        return new Builder();
    }

    public static class Builder {
        private Localidade localidade = new Localidade();

        public Builder setDescricao(String descricao) {
            localidade.descricao = descricao;
            return this;
        }

        public Builder setLatitude(Double latitude) {
            localidade.latitude = latitude;
            return this;
        }
        public Builder setLongitude(Double longitude) {
            localidade.longitude = longitude;
            return this;
        }

        public Localidade commit(){
            localidade.id=UUIDProvider.getNew();
            return localidade;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Localidade that = (Localidade) o;

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
        String result = "Localidade ["
                +"id: " + id
                +", descricao: " + descricao
                + ", latitude: " + latitude
                + ", longitude: " + longitude
                +"]";
        return result;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
