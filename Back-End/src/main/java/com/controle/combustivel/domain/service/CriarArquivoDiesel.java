package com.controle.combustivel.domain.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import com.controle.combustivel.api.model.ConsumoPorMaquina;
import com.controle.combustivel.api.model.SaidaDieselModel;

@Service

public class CriarArquivoDiesel {
	private static final String FILENAME = "C:\\imagensApi\\controleCombustivel\\arquivo_diesel\\diesel.txt";
	private static final String PLANILHA = "C:\\imagensApi\\controleCombustivel\\arquivo_diesel\\consumo.xls";

	


	public void criarExcel(List<SaidaDieselModel> lista) throws IOException {
	    FileWriter arq = new FileWriter(FILENAME);

	    	StringBuilder output = new StringBuilder();
	    	BufferedWriter buffer = new BufferedWriter(arq);
			int rownum = 0;
			for(SaidaDieselModel saida : lista) {
				
				
				//String patern = "YYYY-MM-dd";
				String patern = "ddMMYYYY";
				String data1 = saida.getDataHoraSaida().format(DateTimeFormatter.ofPattern(patern));				
				String data2 = saida.getDataHoraSaida().format(DateTimeFormatter.ofPattern(patern));				
				String cod = "B1030119";				
				
				String ordem = saida.getOrdemInterna();				
				//char[] vetorOrdem = new char[22];
				int tamanhoStr = 22 - ordem.toCharArray().length;
				String str = gerarString(tamanhoStr);
				System.out.println("Tamanho STR:  "+ tamanhoStr);

			
			
				String centroCusto = saida.getCentroCusto();
				String prod = ("70001350");
				
				double qdt = saida.getQuantidade()*100;
				int qtdInteiro = (int) qdt;
				String qtdString = String.valueOf(qtdInteiro);
				tamanhoStr = 13 - qtdString.toCharArray().length;
				qtdString = gerarString(tamanhoStr);

				
				Long desc = saida.getCodigoSap();
				buffer.write(data1+data2+"261"+"B1030119"+ordem+str+prod+"          "+qtdInteiro+qtdString+desc+"\n");
				System.out.println(buffer.toString());

			}
			buffer.flush();
			buffer.close();
		//	arq.write(output.toString());	
			//arq.close();

	}
	
	public String gerarString(int tamanhoStr) {
		System.out.println("Tamanho STR:  "+ tamanhoStr);

		
		String retorno = "";
		System.out.println("Entrando metodo gerarString");
		
		for (int i = 0; i< tamanhoStr; i++) {
		    retorno = retorno.concat(" ");
		}
		System.out.println("tamanho string gerada : "+retorno.length());
		return retorno;
	}
	
	public void criarPlanilha (List<ConsumoPorMaquina> lista) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheetConsumo = workbook.createSheet("Consumo");
		int rownum = 0;
        Row r = sheetConsumo.createRow(rownum++);
        int cell = 0;
        Cell cod = r.createCell(cell ++);
        cod.setCellValue("Codigo SAP");
        Cell ordem = r.createCell(cell ++);
        ordem.setCellValue("Ordem Interna");
        Cell centro = r.createCell(cell ++);
        centro.setCellValue("Centro Custo");
        Cell nome = r.createCell(cell ++);
        nome.setCellValue("Nome Equipamento");
        Cell qtd = r.createCell(cell ++);
        qtd.setCellValue("Quantidade");
        Cell saida = r.createCell(cell ++);
        saida.setCellValue("Saida");
        ZoneId zoneIdBrasilia = ZoneId.of("America/Sao_Paulo");



		for(ConsumoPorMaquina maquina : lista) {
            Row row = sheetConsumo.createRow(rownum++);
            int cellNum = 0;
            Cell codigoSap = row.createCell(cellNum ++);
            codigoSap.setCellValue(maquina.getCodigoSap());
            Cell ordemInterna = row.createCell(cellNum ++);
            ordemInterna.setCellValue(maquina.getOrdemInterna());
            Cell centroCusto = row.createCell(cellNum ++);
            centroCusto.setCellValue(maquina.getCentroCusto());
            Cell nomeEquipamento = row.createCell(cellNum ++);
            nomeEquipamento.setCellValue(maquina.getNomeEquipamento());
            Cell quantidade = row.createCell(cellNum ++);
            quantidade.setCellValue(maquina.getQuantidade());
            
            ZonedDateTime zonedDateTimeBrasilia = maquina.getDataHoraSaida().atZoneSameInstant(zoneIdBrasilia);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDateTime = zonedDateTimeBrasilia.format(formatter);
            Cell dataSaida = row.createCell(cellNum ++);
            dataSaida.setCellValue(formattedDateTime);
			
            FileOutputStream out = new FileOutputStream(new File(PLANILHA));

            workbook.write(out);
            out.close();
            System.out.println("Arquivo Excel criado com sucesso!");
			
		}
	}
}

