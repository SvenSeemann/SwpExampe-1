package fviv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.io.*;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;




import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

@Entity
public class Manager {
	private String lastname, firstname, email, phone;
	@Id
	@GeneratedValue
	private long id;

	@Deprecated
	protected Manager() {
	}

	public Manager(String lastname, String firstname, String email, String phone) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.phone = phone;
	}

	public long getId() {
		return id;
	}

	public String getLastname() {
		return lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public static void pdfvorlagebearbeiten() {
	
		try {
			// (1) Einlesen der PDF-Vorlage
			PdfReader reader = new PdfReader("test_file.pdf");

			// (2) Ziel-PDF bestimmen
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					"change.pdf"));

			// (3) Text-Felder holen
			AcroFields acroFields = stamper.getAcroFields();

			// (4) Felder bearbeiten
			acroFields.setField("ticketart", "Tagesticket");
			acroFields.setField("eventname", "Wonderworld");
			acroFields.setField("number1", "001");
			acroFields.setField("number2", "001");
			acroFields.setField("actors", "Actors");
			acroFields.setField("adressofvenue",
					"The StreetAdress \n youtown \n 666666Dreden ");
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

	public static void barcodegen() throws IOException, BarcodeException {
		// get a Barcode from the BarcodeFactory
		Barcode barcode = BarcodeFactory.createPost("string here"); // hier
																		// der
																		// code
																		// später
																		// rein
		try {
			File f = new File("out.png");

			// Let the barcode image handler do the hard work
			BarcodeImageHandler.savePNG(barcode, f);
		} catch (Exception e) {
			// Error handling here
		}
	}

	public static void addbarcode() {
		 int id = 0;
			String name;
			name= "ticket" + id;
		try {
			PdfReader pdfReader = new PdfReader("change.pdf");
			
			PdfStamper pdfStamper = new PdfStamper(pdfReader,
					new FileOutputStream("src/main/webapp/"+name+".pdf"));

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
