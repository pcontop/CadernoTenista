package br.com.pcontop.CadernoOlheiro.model.export;

import br.com.pcontop.CadernoOlheiro.model.export.restSpring.RestExporter;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 27/11/13
 * Time: 23:45
 * To change this template use File | Settings | File Templates.
 */
public class ExporterFactory {
    private static final Exporter exporter = new RestExporter();/*new FileXmlExporter();*/
    public static Exporter getInstance(){
        return exporter;
    }
}
