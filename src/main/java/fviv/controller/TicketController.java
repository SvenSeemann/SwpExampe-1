package fviv.controller;

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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import fviv.ticket.TicketRepository;
import fviv.ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

// du musst noch anzahl und prüfung

@Controller
public class TicketController {
	private static float preis;
	private static boolean ticketart;
	private final TicketRepository ticketRepository;
	private static long ticketid;

	@Autowired
	public TicketController(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@RequestMapping({ "/ticket" })
	public String index() {
		return "ticket";
	}

	@RequestMapping(value = "/newTicket", method = RequestMethod.POST)
	public String newTicket(@RequestParam("ticketart") boolean ticketart,
			@RequestParam("price") float kosten) throws IOException,
			BarcodeException {
		// Create Ticket
		setPreis(kosten);
		setTicketart(ticketart);
		boolean checked = false;
		Ticket ticket = new Ticket(ticketart, checked); // Eins ist gleich
														// Tagesticket // Null
														// ist gleich
														// 3Tagesticket
		ticketRepository.save(ticket);
		setTicketid(ticket.getId());
		pdfvorlagebearbeiten();
		barcodegen();
		addbarcode();
		return "ticket";
	}

	// true = tagesticket
	// false == 3tagesticket

	public static String ticketarthelper(boolean ticketart) {
		if (ticketart == true) {
			return "Tagesticket";
		} else
			return "3-Tagesticket";
	}

	@RequestMapping({ "/ticketDrucken" })
	public String ticketDrucken() {
		return "redirect:/ticket" + ticketid + ".pdf";
	}

	public static void pdfvorlagebearbeiten() throws IOException,
			BarcodeException {
		float dummy = getPreis();
		String price = "" + dummy + "0 Euro";
		try {
			// (1) Einlesen der PDF-Vorlage
			PdfReader reader = new PdfReader("test_file.pdf");

			// (2) Ziel-PDF bestimmen
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					"change.pdf"));

			// (3) Text-Felder holen
			AcroFields acroFields = stamper.getAcroFields();

			// (4) Felder bearbeiten
			acroFields.setField("ticketart", ticketarthelper(ticketart));
			acroFields.setField("eventname", "Wonderworld");
			acroFields.setField("number1", ticketid + "");
			acroFields.setField("number2", ticketid + "");
			acroFields.setField("actors", "Actors");
			acroFields.setField("adressofvenue",
					"The StreetAdress \n youtown \n 666666Dreden ");
			acroFields.setField("date", "66-66-6666");
			acroFields.setField("price", price);
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

	public static void barcodegen() throws IOException, BarcodeException {
		// get a Barcode from the BarcodeFactory
		Barcode barcode = BarcodeFactory.createCode128B("" + ticketid); // hier
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
		String name;
		name = "ticket" + ticketid;
		try {
			PdfReader pdfReader = new PdfReader("change.pdf");

			PdfStamper pdfStamper = new PdfStamper(pdfReader,
					new FileOutputStream("src/main/webapp/" + name + ".pdf"));

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

	public static float getPreis() {
		return preis;
	}

	public void setPreis(float preis) {
		TicketController.preis = preis;
	}

	public static boolean getTicketart() {
		return ticketart;
	}

	public static void setTicketart(boolean ticketart) {
		TicketController.ticketart = ticketart;
	}

	public static long getTicketid() {
		return ticketid;
	}

	public static void setTicketid(long ticketid) {
		TicketController.ticketid = ticketid;
	}
}
