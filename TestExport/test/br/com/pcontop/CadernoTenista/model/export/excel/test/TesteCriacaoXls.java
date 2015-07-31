package br.com.pcontop.CadernoTenista.model.export.excel.test;

import org.junit.Test;

import java.io.File;

import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.bean.br.com.pcontop.CadernoOlheiro.bean.test.PartidaHelper;
import br.com.pcontop.CadernoTenista.model.export.ExporterException;
import br.com.pcontop.CadernoTenista.model.export.excel.file.FileExporterExcel;
import br.com.pcontop.CadernoTenista.model.export.file.FileExporter;
import br.com.pcontop.CadernoTenista.model.export.file.ImpossivelCriarDiretorioException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by PauloBruno on 12/01/14.
 */
public class TesteCriacaoXls {
    @Test
    public void testeCriacao(){
        FileExporter fileExporter = new FileExporterExcel();
        Partida partida = PartidaHelper.buildPartida();
        try {
            fileExporter.export(null, partida);
        } catch (ExporterException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        try {
            File file = fileExporter.getArquivoPartida(partida,null);
            assertNotNull(file);
            assert(file.exists());
            assert(file.getName()!=null);
            assert(file.getName().endsWith(".xls"));
        } catch (ImpossivelCriarDiretorioException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
