/**
  * Created by master on 20/7/16.
  */
import java.io.FileInputStream
import org.apache.poi.hwpf.HWPFDocument
import org.apache.poi.hwpf.extractor.WordExtractor
import org.apache.poi.POIXMLProperties.CoreProperties
import org.apache.poi.hpsf.PropertySet
import org.apache.poi.hpsf.SummaryInformation
import org.apache.poi.hwpf.HWPFDocument
import org.apache.poi.poifs.filesystem.DirectoryEntry
import org.apache.poi.poifs.filesystem.DocumentEntry
import org.apache.poi.poifs.filesystem.DocumentInputStream
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem
import org.apache.poi.xwpf.extractor.XWPFWordExtractor
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.openxml4j.opc.OPCPackage

import org.apache.poi.POIXMLTextExtractor
object DocxToText {
  def main(args: Array[String]) {
    var extractor: WordExtractor = null
    try {
      val fileis = new FileInputStream("/home/master/Desktop/resume.doc")

      val docs = new HWPFDocument(fileis)
      extractor = new WordExtractor(docs)
      val fileData = extractor.getParagraphText
      for (i <- 0 until fileData.length if fileData(i) != null) println(fileData(i))
      /**/val poifs:NPOIFSFileSystem = new NPOIFSFileSystem(fileis)
      /**/val dir = poifs.getRoot
      /**/var si: SummaryInformation = null
      /**/val siEntry = dir.getEntry(SummaryInformation.DEFAULT_STREAM_NAME).asInstanceOf[DocumentEntry]
      /**/val dis = new DocumentInputStream(siEntry)
      /**/val ps = new PropertySet(dis)
      /**/si = new SummaryInformation(ps)
      println(si.getTitle)

      val info:POIXMLTextExtractor = new POIXMLTextExtractor(fileis)

     val pkg:OPCPackage  = OPCPackage.open(fileis);
      POIXMLProperties props = new POIXMLProperties(pkg);
      PackagePropertiesPart ppropsPart = props.getCoreProperties().getUnderlyingProperties();

      Date created = ppropsPart.getCreatedProperty().getValue();
      Date modified = ppropsPart.getModifiedProperty().getValue();

      String lastModifiedBy = ppropsPart.getLastModifiedByProperty().getValue();

      //val info:CoreProperties = extractor.getCoreProperties
      //println(extractor)

    }
    catch {
      case ex: Exception => ex.printStackTrace()
    }
  }
}
