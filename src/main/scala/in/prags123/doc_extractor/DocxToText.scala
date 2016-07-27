package in.prags123.doc_extractor

import org.apache.poi.POIXMLProperties
import org.apache.poi.hpsf.{PropertySet, SummaryInformation}
import org.apache.poi.hwpf.HWPFDocument
import org.apache.poi.hwpf.extractor.WordExtractor
import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.poifs.filesystem.{DocumentEntry, DocumentInputStream, NPOIFSFileSystem}

object DocxToText {
  def main(args: Array[String]) {
    var extractor: WordExtractor = null
    try {
      // Ash: DOC file
      val docFile = getClass.getResourceAsStream("/resume.doc")
      val docs = new HWPFDocument(docFile)
      extractor = new WordExtractor(docs)
      val fileData = extractor.getParagraphText
      for (i <- 0 until fileData.length if fileData(i) != null) println(fileData(i))

      // Ash: Re-opening the DOC file since we're re-parsing the document
      val docFile2 = getClass.getResourceAsStream("/resume.doc")
      val poifs: NPOIFSFileSystem = new NPOIFSFileSystem(docFile2)
      val dir = poifs.getRoot
      val siEntry = dir.getEntry(SummaryInformation.DEFAULT_STREAM_NAME).asInstanceOf[DocumentEntry]
      val dis = new DocumentInputStream(siEntry)
      val ps = new PropertySet(dis)
      val si = new SummaryInformation(ps)
      println(si.getAuthor)
      println(si.getApplicationName)
      println(si.getCreateDateTime)
      println(si.getLastSaveDateTime)
      println(si.getPageCount)

      println("------- End of DOC File ----------")

      println("------- Start of DOCX File ----------")

      // OPCPackage is used for handling XML based files, which is why we use docx instead of doc
      val docxFile = getClass.getResourceAsStream("/resume.docx")
      val pkg: OPCPackage = OPCPackage.open(docxFile)
      val props = new POIXMLProperties(pkg)
      val coreProps = props.getCoreProperties
      val ppropsPart = coreProps.getUnderlyingProperties()
      val created = ppropsPart.getCreatedProperty().getValue()
      println(created)
      val modified = ppropsPart.getModifiedProperty().getValue()
      println(modified)
      val lastModifiedBy = ppropsPart.getLastModifiedByProperty().getValue()
      println(lastModifiedBy)
      val titleProperty = ppropsPart.getTitleProperty.getValue
      println(titleProperty)

      val author = coreProps.getCreator
      println(author)
      val description = coreProps.getDescription
      println(description)

      println("------- End of DOCX File ----------")
    }
    catch {
      case ex: Exception => ex.printStackTrace()
    }
  }
}
