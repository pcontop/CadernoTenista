package br.com.pcontop.CadernoOlheiro.model.export.excel.file;

import android.content.Context;
import android.util.Log;
import br.com.pcontop.CadernoOlheiro.bean.EventoJogo;
import br.com.pcontop.CadernoOlheiro.bean.Jogador;
import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.bean.Time;
import br.com.pcontop.CadernoOlheiro.helper.TempoHelper;
import br.com.pcontop.CadernoOlheiro.model.TextTranslator;
import br.com.pcontop.CadernoOlheiro.model.export.ExporterException;
import br.com.pcontop.CadernoOlheiro.model.export.file.FileExporter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by PauloBruno on 12/01/14.
 */
public class FileExporterExcel extends FileExporter {
    private static  Map<String, CellStyle> styles;
    private static Context context;
    private Partida partida;


    @Override
    public boolean export(Context context, Partida partida) throws ExporterException {
        this.context=context;
        this.partida=partida;
        Workbook workbook = buildWorkbook(partida);
        try {
            File arquivo = getArquivoPartida(partida, context);
            FileOutputStream fileOutputStream = new FileOutputStream(arquivo);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            Log.e("FileExporter", "Erro ao exportar", e);
            throw new ExporterException();
        }
    }

    private Workbook buildWorkbook(Partida partida) {
        Workbook workbook = new HSSFWorkbook();
        styles = createStyles(workbook);
        adicionePartida(workbook, partida);
        adicioneJogadores(workbook, partida.getTime1());
        adicioneJogadores(workbook, partida.getTime2());
        return workbook;
    }

    private void adicioneJogadores(Workbook workbook, Time time) {
        adicioneJogadores(workbook, time, time.getJogadores());
    }

    private void adicioneJogadores(Workbook workbook, Time time, Set<Jogador> jogadores) {
        for (Jogador jogador: jogadores){
            adicioneJogador(workbook,time, jogador);
        }
    }

    private void adicioneJogador(Workbook workbook, Time time, Jogador jogador) {
        String nomeSheet = time.getNome() + "-" + jogador.getNome();
        nomeSheet = verifiqueExistente(workbook, nomeSheet);
        Sheet sheet = workbook.createSheet(nomeSheet);
        adicioneCabecalhoJogador(sheet);
        for (int i=0;i<jogador.getEventos().size();i++){
            EventoJogo eventoJogo = jogador.getEventos().get(i);
            adicioneEvento(sheet, i+1, eventoJogo);
        }
        //sheet.autoSizeColumn(0);
        //sheet.autoSizeColumn(1);
    }

    private String verifiqueExistente(Workbook workbook, String nomeSheet) {
        if (workbook.getSheet(nomeSheet)==null){
            return nomeSheet;
        }
        int contador=1;
        String novoNomeSheet = "";
        do {
            novoNomeSheet = nomeSheet + "_" + contador;
            contador++;

        } while (workbook.getSheet(novoNomeSheet)!=null);
        return novoNomeSheet;
    }

    private void adicioneCabecalhoJogador(Sheet sheet) {
        Row row = sheet.createRow(0);
        adicioneCabecalho(row, 0, "Tempo");
        adicioneCabecalho(row, 1, "Decorridos");
        adicioneCabecalho(row, 2, "Momento");
        adicioneCabecalho(row, 3, "Jogada");
    }

    private void adicioneCabecalho(Row row, int colNum, String texto) {
        Cell cell = row.createCell(colNum);
        cell.setCellValue(texto);
        cell.setCellStyle(styles.get("header"));
    }

    private String getTempoTraduzido(Context context, Partida partida, Date hora){
        String descricaoTempo = TempoHelper.getDescricaoTempo(partida, hora);
        String tempoTraduzido = TextTranslator.translateFromStringName(context, descricaoTempo);
        return tempoTraduzido;
    }

    private void adicioneEvento(Sheet sheet, int linha, EventoJogo eventoJogo) {
        Row linha1 =sheet.createRow(linha);
        Cell cell = linha1.createCell(0);
        String tempo = getTempoTraduzido(context, partida, eventoJogo.getHora());
        cell.setCellValue(tempo);
        cell.setCellStyle(styles.get("cell_normal"));

        cell = linha1.createCell(1);
        Date diferenca = TempoHelper.tempoDesdeUltimoInicio(partida, eventoJogo.getHora());
        if (diferenca!=null){
            String textoDiferenca = TempoHelper.getTextoDiferencaTempo(diferenca);
            cell.setCellValue(textoDiferenca);
        }
        cell.setCellStyle(styles.get("cell_normal"));

        cell = linha1.createCell(2);
        cell.setCellValue(eventoJogo.getHora());
        cell.setCellStyle(styles.get("cell_normal_date"));

        cell = linha1.createCell(3);
        String tipoEvento = TextTranslator.translateFromStringName(context, eventoJogo.getTipoEvento().getDescricao());
        cell.setCellValue(tipoEvento);
        cell.setCellStyle(styles.get("cell_normal"));

    }


    private void adicionePartida(Workbook workbook, Partida partida) {
        Sheet sheet = workbook.createSheet("Partida");
        String local = partida.getLocal()==null?"":partida.getLocal().getDescricao();
        adicioneTexto(sheet, 0, "Local", local);
        adicioneData(sheet, 1, "Início Primeiro Tempo", partida.getDataInicio());
        adicioneData(sheet, 2, "Fim Primeiro Tempo", partida.getDataFimPrimeiroTempo());
        adicioneData(sheet, 3, "Início Segundo Tempo", partida.getDataInicioSegundoTempo());
        adicioneData(sheet, 4, "Fim Segundo Tempo", partida.getDataFimSegundoTempo());
        //sheet.autoSizeColumn(0);
        //sheet.autoSizeColumn(1);
    }

    private void adicioneTexto(Sheet sheet, int linha, String titulo, String texto) {
        Row linha1 =sheet.createRow(linha);
        Cell cell = linha1.createCell(0);
        cell.setCellValue(titulo + ":");
        cell.setCellStyle(styles.get("cell_b"));
        cell = linha1.createCell(1);
        cell.setCellValue(texto);
        cell.setCellStyle(styles.get("cell_normal"));
    }

    private void adicioneData(Sheet sheet, int linha, String titulo, Date data) {
        Row linha1 =sheet.createRow(linha);
        Cell cell = linha1.createCell(0);
        cell.setCellValue(titulo + ":");
        cell.setCellStyle(styles.get("cell_b"));
        cell = linha1.createCell(1);
        cell.setCellValue(data);
        cell.setCellStyle(styles.get("cell_normal_date"));
    }

    @Override
    public String getSufixo() {
        return ".xls";
    }

    /**
     * create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        DataFormat df = wb.createDataFormat();
        String dateString="d/m/yyyy hh:mm:ss";
        String timeString="hh:mm:ss";

        CellStyle style;
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        style.setDataFormat(df.getFormat(dateString));
        styles.put("header_date", style);

        Font font1 = wb.createFont();
        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font1);
        styles.put("cell_b", style);

        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font1);
        styles.put("cell_b_centered", style);

        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setDataFormat(df.getFormat("d/m/yy h:mm"));
        styles.put("cell_b_date", style);

        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat(dateString));
        styles.put("cell_g", style);

        Font font2 = wb.createFont();
        font2.setColor(IndexedColors.BLUE.getIndex());
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font2);
        styles.put("cell_bb", style);

        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat(dateString));
        styles.put("cell_bg", style);

        Font font3 = wb.createFont();
        font3.setFontHeightInPoints((short)14);
        font3.setColor(IndexedColors.DARK_BLUE.getIndex());
        font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font3);
        style.setWrapText(true);
        styles.put("cell_h", style);

        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setWrapText(true);
        styles.put("cell_normal", style);

        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        styles.put("cell_normal_centered", style);

        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat(dateString));
        styles.put("cell_normal_date", style);

        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat(timeString));
        styles.put("cell_normal_time", style);

        style = createStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setIndention((short)1);
        style.setWrapText(true);
        styles.put("cell_indented", style);

        style = createStyle(wb);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cell_blue", style);

        return styles;
    }


    private static CellStyle createStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        /*
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        */
        return style;
    }
}
