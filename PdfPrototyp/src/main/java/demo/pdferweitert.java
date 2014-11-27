package demo;


import java.awt.Color;
import java.io.*;
import java.net.MalformedURLException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class pdferweitert
{
   public static void main( String[] args ) throws DocumentException, MalformedURLException, IOException
   {
      String outputFile = "MeineZweitePdfDateiMitIText.pdf";

      Document document = new Document( PageSize.A4 );
      PdfWriter.getInstance( document, new FileOutputStream( outputFile ) );
      document.open();

      Font f1 = new Font( Font.HELVETICA, 12, Font.BOLD   );
      Font f2 = new Font( Font.COURIER,   10, Font.NORMAL );
      Font f3 = new Font( Font.HELVETICA, 10, Font.NORMAL );
      Paragraph p = new Paragraph();
      p.setSpacingBefore( 10 );
      p.setSpacingAfter(  10 );

      p.setFont( f1 );
      p.add( new Phrase( "Registrierte Fonts:\n" ) );
      p.setFont( f2 );
      p.add( new Phrase( "" + FontFactory.getRegisteredFonts() ) );
      document.add( p );

      p.clear();
      p.setFont( f3 );
      f3.setColor( Color.RED );
      p.add( new Phrase( "Rot, " ) );
      f3.setColor( new CMYKColor( 1.f, 0f, 0f, 0f ) );
      p.add( new Phrase( "Cyan, " ) );
      f3.setColor( new CMYKColor( 0f, 1.f, 0f, 0f ) );
      p.add( new Phrase( "Magenta " ) );
      document.add( p );
      f3.setColor( Color.BLUE );

      p.clear();
      List lst1 = new List( true, 15 );
      lst1.add( "Unterliste mit Strichen" );
      List lst2 = new List( false, 10 );
      lst2.add( "Item1a" );
      lst2.add( "Item1b" );
      lst1.add( lst2 );
      lst1.add( "Unterliste mit Buchstaben" );
      List lst3 = new List( false, true );
      lst3.add( "Item2a" );
      lst3.add( "Item2b" );
      lst1.add( lst3 );
      document.add( lst1 );

      p.clear();
      p.setIndentationLeft(  200 );
      p.setIndentationRight( 200 );
      p.setAlignment( Element.ALIGN_JUSTIFIED );
      Phrase ph = new Phrase( "Dies ist mein sehr langer Text, mit dem Absatzabstände, Einrückung und Blocksatz demonstriert werden." );
      p.add( ph );
      document.add( p );

      File meineBilddatei = new File( "C:/Users/Niko/workspace/pdfeditoring/bild.jpg" );
      if( meineBilddatei.exists() ) {
         Jpeg jpg = new Jpeg( meineBilddatei.toURI().toURL() );
         jpg.setAlignment( Element.ALIGN_CENTER );
         jpg.scalePercent( 50 );
         document.add( jpg );
      }

      document.close();
   }
}