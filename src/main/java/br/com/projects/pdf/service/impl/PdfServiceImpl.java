package br.com.projects.pdf.service.impl;

import br.com.projects.pdf.dto.request.PdfRequest;
import br.com.projects.pdf.service.PdfService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public byte[] mergePdf(PdfRequest pdfRequest) {

        String pdfName = pdfRequest.getPdfName();
        List<String> paths = pdfRequest.getPaths();

        List<InputStream> list = new ArrayList<>();
        byte[] array;

        try {

            paths.forEach(it -> {
                try {
                    list.add(new FileInputStream(new File(it)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });

            String pathTemp = System
                .getProperty("user.dir"); //root directory of where your program runs
            String name = "/pdf/" + pdfName + ".pdf";
            String name2 = pathTemp + name;
            Path path = Paths.get(name2);

            // Resulting pdf
            Files.createDirectories(path.getParent());
            OutputStream out = new FileOutputStream(new File(name2));

            this.doMerge(list, out);

            array = Files.readAllBytes(Paths.get(name2));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return array;
    }

    private void doMerge(List<InputStream> list, OutputStream outputStream)
        throws DocumentException, IOException {

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        for (InputStream in : list) {

            PdfReader reader = new PdfReader(in);

            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
        }
        outputStream.flush();
        document.close();
        outputStream.close();
    }
}