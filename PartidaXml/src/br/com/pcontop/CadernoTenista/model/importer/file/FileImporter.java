package br.com.pcontop.CadernoTenista.model.importer.file;

import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.model.importer.Importer;
import br.com.pcontop.CadernoTenista.model.importer.ImporterException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by PauloBruno on 25/01/14.
 */
public abstract class FileImporter implements Importer {
    public Partida leiaPartida(File file) throws ImporterException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ImporterException(e.getMessage());
        }
        Partida partida = leiaPartida(fileInputStream);
        return partida;
    }
}
