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

	@RequestMapping({ "/ticketPruefen" })
	public String ticketpruefen() {
		return "ticketpruefung";
	}

	@RequestMapping(value = "/pruefeTicket", method = RequestMethod.POST)
	// ticketeinchecken methode
	public String pruefeTicket(@RequestParam("ticketart") boolean ticketart,
			@RequestParam("numbers") long id) {
		Ticket ticketkontrolle = ticketRepository.findById(id);
		if (ticketRepository.findById(id) == null) {
			return "redirect:/ticket";
		}
		if (ticketkontrolle.getChecked() == true) {
			System.out.println("" + id + "already checked in!!!");
			return "ticketpruefung";
		} else {
			ticketkontrolle.setChecked(true);
			System.out.println("" + id + "now checked in");
			ticketRepository.save(ticketkontrolle);
			return "ticketpruefung";
		}
	}

	@RequestMapping(value = "/newTicket", method = RequestMethod.POST)
	public String newTicket(@RequestParam("ticketart") boolean ticketart,
			@RequestParam("price") float kosten,
			@RequestParam("numbers") int anzahl) throws IOException,
			BarcodeException {
		for (int i = 1; i <= anzahl; i++) {
			// Create Ticket
			Ticket ticket = new Ticket(ticketart, false); // Eins ist gleich
															// Tagesticket //
															// Null
															// ist gleich
															// 3Tagesticket
			ticketRepository.save(ticket);
			setTicketid(ticket.getId());
			pdfvorlagebearbeiten(kosten, ticketart);
			barcodegen();
			addbarcode();
		}
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
	public String ticketDrucken(
			@RequestParam(value = "ticketnummer", required = false) String ticketnmr) {
		if (ticketnmr == "") {
			ticketnmr = "" + ticketid;
		} else {
			long ticketnummer = Long.parseLong(ticketnmr);

			if (ticketnummer > ticketid) {
				return "ticket";
			} else {
				if (ticketnummer > 0) {
					return "redirect:/ticket" + ticketnummer + ".pdf";
				} else
					return "redirect:/ticket" + ticketnummer + ".pdf";
			}
		}
		return "redirect:/ticket" + ticketid + ".pdf";
	}

	public static void pdfvorlagebearbeiten(float ticketkosten,
			boolean ticketart) throws IOException, BarcodeException {
		String price = "" + ticketkosten + " Euro";
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

	public static long getTicketid() {
		return ticketid;
	}

	public static void setTicketid(long ticketid) {
		TicketController.ticketid = ticketid;
	}

}
