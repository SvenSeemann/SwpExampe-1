package pdfeditoring;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sourceforge.barbecue.*; 

import javax.swing.*;

import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;
import com.itextpdf.text.pdf.AcroFields.Item;

@SuppressWarnings("unused")
public class pdftest {
	
	public static void main(String[] args) throws BarcodeException {

		try {
			// (1) Einlesen der PDF-Vorlage
			PdfReader reader = new PdfReader(
					"test_file.pdf");

			// (2) Ziel-PDF bestimmen
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					"change.pdf"));

			// (3) Text-Felder holen
			AcroFields acroFields = stamper.getAcroFields();

			// (4) Felder bearbeiten
			acroFields.setField("eventname", "Wonderworld");
			acroFields.setField("number1","001");
			acroFields.setField("number2","001");
			acroFields.setField("actors", "Deine Mama");
			acroFields.setField("adressofvenue", "The StreetAdress , youtown, 666666Dreden ");
			acroFields.setField("date", "66-66-6666");
			acroFields.setField("price", "15.00€");
			acroFields.setField("eventnamesmall", "Wunderland");
			acroFields.setField("datesmall", "66-66-6666€");

			// (5) Dokumente schließen
			stamper.close();
			reader.close();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		barcodegen();
		addbarcode();
	}

	
	public static void barcodegen() throws BarcodeException {
		// get a Barcode from the BarcodeFactory
		Barcode barcode = BarcodeFactory.createCode128B("string here"); // hier
																		// der
																		// code
																		// später
																		// rein
		try {
			File f = new File("gggggg.png");

			// Let the barcode image handler do the hard work
			BarcodeImageHandler.savePNG(barcode, f);
		} catch (Exception e) {
			// Error handling here
		}
	}

	public static void addbarcode() {
		try {
			PdfReader pdfReader = new PdfReader(
					"change.pdf");

			PdfStamper pdfStamper = new PdfStamper(pdfReader,
					new FileOutputStream("changedd.pdf"));

			Image image = Image.getInstance("gggggg.png");

			PdfContentByte content = pdfStamper.getUnderContent(1);

			image.setAbsolutePosition(530f, 655f);
		    image.setRotationDegrees(90f);
		    image.scalePercent(50f);

			content.addImage(image);

			pdfStamper.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();

		}
	}
}
