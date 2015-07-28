package br.com.pcontop.CadernoOlheiro.helper;

import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.bean.TempoPartida;
import br.com.pcontop.CadernoOlheiro.bean.TiposTempoPartida;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by PauloBruno on 13/01/14.
 */
public class TempoHelper {
    private TempoHelper(){

    }

    public static Date getTempoTranscorridoDesdeUltimoInicio(Partida partida, Date momento){
        for (TempoPartida tempoPartida: partida.getTemposPartida()){
            if (momento.compareTo(tempoPartida.getDataInicio())>=0 &&
            momento.compareTo(tempoPartida.getDataFim())<=0){
                return subtraiaData(momento, tempoPartida.getDataFim());
            }
        }
        return null;
    }

    private static Date subtraiaData(Date data, Date dataInicio) {
        Date diferenca = new Date(data.getTime() - dataInicio.getTime());
        return diferenca;
    }

    public static String getTextoDiferencaTempo(Date diferenca) {
        if (diferenca==null){
            return "00:00:00";
        }
        long diff = diferenca.getTime();
        Long diffSeconds = diff / 1000 % 60;
        Long diffMinutes = diff / (60 * 1000) % 60;
        Long diffHours = diff / (60 * 60 * 1000);
        return String.format("%02d:%02d:%02d",diffHours, diffMinutes ,diffSeconds);
    }

    public static Long getSegundosTranscorridos(Date diferenca){
        if (diferenca==null){
            return (long)0;
        }

        Long diff = diferenca.getTime();
        Long diffSeconds = diff / 1000;
        return diffSeconds;
    }

}
