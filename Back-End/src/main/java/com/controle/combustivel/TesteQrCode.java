package com.controle.combustivel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


public class TesteQrCode {
	public static void main(String[] args) throws IOException, WriterException {
		
		String nome = "10033218";
		String charset = "UTF-8";
		FileAttribute<?>attribute ;
		//attribute.name();
		Path path = Path.of("C:\\imagensApi\\controleCombustivel\\qr_code\\"+nome+".png");
		
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		String dataCharset = new String(nome.getBytes(charset), charset );
		BitMatrix bitMatrix =  qrCodeWriter.encode(dataCharset, BarcodeFormat.QR_CODE, 128, 128);
		
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	}

}
