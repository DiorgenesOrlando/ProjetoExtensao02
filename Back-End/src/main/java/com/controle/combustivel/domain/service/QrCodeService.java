package com.controle.combustivel.domain.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QrCodeService {
	public void gerarQrCodeFile(String nome) throws IOException, WriterException {
		
		String charset = "UTF-8";
		Path path = Path.of("C:\\imagensApi\\controleCombustivel\\qr_code\\"+nome+".png");
		
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		String dataCharset = new String(nome.getBytes(charset), charset );
		BitMatrix bitMatrix =  qrCodeWriter.encode(dataCharset, BarcodeFormat.QR_CODE, 128, 128);
		
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	}
}
