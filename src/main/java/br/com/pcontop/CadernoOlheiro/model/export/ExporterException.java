package br.com.pcontop.CadernoOlheiro.model.export;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 27/11/13
 * Time: 23:40
 * To change this template use File | Settings | File Templates.
 */
public class ExporterException extends Exception {
    public ExporterException(Exception e){
        super(e);
    }

    public ExporterException(){

    }
}
