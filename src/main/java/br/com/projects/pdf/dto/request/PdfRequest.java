package br.com.projects.pdf.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PdfRequest {

    private String pdfName;
    private List<String> paths;
}