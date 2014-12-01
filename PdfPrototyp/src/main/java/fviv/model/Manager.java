package fviv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.awt.image.BufferedImage;
import java.io.*;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;


@Entity
public class Manager{
	private String lastname, firstname, email, phone;
		
	@Id
	@GeneratedValue
	private long id;
	
	@Deprecated
	protected Manager(){}
	
	public Manager(String lastname, String firstname, String email, String phone){
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.phone = phone;
	}
	
	public long getId(){
		return id;
	}
	
	public String getLastname(){
		return lastname;
	}
	
	public String getFirstname(){
		return firstname;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPhone(){
		return phone;
	}
	public static void pdfvorlagebearbeiten(){

		try {
			// (1) Einlesen der PDF-Vorlage
			PdfReader reader = new PdfReader("test_file.pdf");

			// (2) Ziel-PDF bestimmen
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					"change.pdf"));

			// (3) Text-Felder holen
			AcroFields acroFields = stamper.getAcroFields();

			// (4) Felder bearbeiten
			acroFields.setField("eventname", "Wonderworld");
			acroFields.setField("number1", "001");
			acroFields.setField("number2", "001");
			acroFields.setField("actors", "Deine Mama");
			acroFields.setField("adressofvenue",
					"The StreetAdress , youtown, 666666Dreden ");
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

	}
	public static void barcodegen() throws IOException{

		//Create the barcode bean
		Code39Bean bean = new Code39Bean();

		final int dpi = 150;

		//Configure the barcode generator
		bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar 
		                                                 //width exactly one pixel
		bean.setWideFactor(3);
		bean.doQuietZone(false);

		//Open output file
		File outputFile = new File("out.png");
		OutputStream out = new FileOutputStream(outputFile);
		try {
		    //Set up the canvas provider for monochrome PNG output 
		    BitmapCanvasProvider canvas = new BitmapCanvasProvider(
		            out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

		    //Generate the barcode
		    bean.generateBarcode(canvas, "text here");//barcode here

		    //Signal end of generation
		    canvas.finish();
		} finally {
		    out.close();
		}
	}

	public static void addbarcode() {
		try {
			PdfReader pdfReader = new PdfReader("change.pdf");

			PdfStamper pdfStamper = new PdfStamper(pdfReader,
					new FileOutputStream("src/main/webapp/changedd.pdf"));

			Image image = Image.getInstance("out.png");

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
