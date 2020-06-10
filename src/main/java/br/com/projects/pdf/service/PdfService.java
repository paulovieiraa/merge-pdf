package br.com.projects.pdf.service;

import br.com.projects.pdf.dto.request.PdfRequest;

public interface PdfService {

   byte[] mergePdf(PdfRequest pdfRequest);
}