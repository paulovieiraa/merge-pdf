package br.com.projects.pdf.controller;

import br.com.projects.pdf.dto.request.PdfRequest;
import br.com.projects.pdf.service.PdfService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pdf")
@CrossOrigin(origins = "*")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/merge", produces = "application/pdf")
    public byte[] mergePdf(@RequestBody PdfRequest pdfRequest) {
        return pdfService.mergePdf(pdfRequest);
    }
}