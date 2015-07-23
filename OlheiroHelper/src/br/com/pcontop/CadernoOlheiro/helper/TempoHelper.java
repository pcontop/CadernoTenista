package br.com.pcontop.CadernoOlheiro.helper;

import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.bean.TemposPartida;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by PauloBruno on 13/01/14.
 */
public class TempoHelper {
    private TempoHelper(){

    }

    private static String getNomeTempo(TemposPartida temposPartida){
        String description = temposPartida.getDescricao();
        return description;
    }

    public static TemposPartida getTempoJogo(Partida partida, Date data){
        if (partida==null
                || partida.getDataInicio()==null
                || partida.getDataFimPrimeiroTempo() ==null
                || partida.getDataInicioSegundoTempo()==null
                || partida.getDataFimSegundoTempo()==null
                || data==null){
            return TemposPartida.INDEFINIDA;
        }
        Calendar dataVerificada = getCalendarFromDate(data);

        Calendar inicioJogo =getCalendarFromDate(partida.getDataInicio());

        if (dataVerificada.before(inicioJogo)){
            return TemposPartida.ANTES_JOGO;
        }

        Calendar fimPrimeiroTempo = getCalendarFromDate(partida.getDataFimPrimeiroTempo());
        if (dataVerificada.after(inicioJogo) && dataVerificada.before(fimPrimeiroTempo)){
            return TemposPartida.PRIMEIRO_TEMPO;
        }

        Calendar inicioSegundoTempo = getCalendarFromDate(partida.getDataInicioSegundoTempo());
        if (dataVerificada.after(fimPrimeiroTempo) && dataVerificada.before(inicioSegundoTempo)){
            return TemposPartida.INTERVALO;
        }

        Calendar fimSegundoTempo = getCalendarFromDate(partida.getDataFimSegundoTempo());
        if (dataVerificada.after(inicioSegundoTempo) && dataVerificada.before(fimSegundoTempo)){
            return TemposPartida.SEGUNDO_TEMPO;
        }

        return  TemposPartida.APOS_JOGO;

    }

    public static String getDescricaoTempo(Partida partida, Date data){
        TemposPartida tempoJogo = getTempoJogo(partida,data);
        return tempoJogo.getDescricao();

    }

    private static Calendar getCalendarFromDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date tempoDesdeUltimoInicio(Partida partida, Date data){
        TemposPartida tempoData = getTempoJogo(partida, data);
        switch (tempoData){
            case INDEFINIDA:
                return null;
            case ANTES_JOGO:
                return null;
            case PRIMEIRO_TEMPO:
                return subtraiaData(data, partida.getDataInicio());
            case INTERVALO:
                return null;
            case SEGUNDO_TEMPO:
                return subtraiaData(data, partida.getDataInicioSegundoTempo());
            case APOS_JOGO:
                return null;
            default:
                return null;
        }

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
