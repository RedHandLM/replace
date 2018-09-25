import com.aspose.pdf.*;

import java.io.IOException;

/**
 * Created by liushichang on 2018/9/21.
 */
public class PdfUtils extends AbstractUtils {

    public static void replace(String srcPath, String targetPath){
        try{
            setLicense();
            TextReplaceOptions tro = new TextReplaceOptions(TextReplaceOptions.Scope.REPLACE_ALL);
            Document pdfDoc = new Document(srcPath);
            TextFragmentAbsorber textFragmentAbsorber = new TextFragmentAbsorber("\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}", new TextSearchOptions(true));
            textFragmentAbsorber.setTextReplaceOptions(tro);
            PageCollection pages = pdfDoc.getPages();
            TextFragmentAbsorber textFragmentAbsorber2 = new TextFragmentAbsorber("\\d{11}", new TextSearchOptions(true));
            textFragmentAbsorber2.setTextReplaceOptions(tro);
            pages.accept(textFragmentAbsorber);
            pages.accept(textFragmentAbsorber2);
            for (TextFragment textFragment : (Iterable<TextFragment>) textFragmentAbsorber.getTextFragments()) {
                String email = textFragment.getText();
                if(isBlank(email)){
                    continue;
                }
                textFragment.setText("*****@***.com");
                textFragment.getTextState().setBackgroundColor(com.aspose.pdf.Color.getWhite());
            }
            for (TextFragment textFragment : (Iterable<TextFragment>) textFragmentAbsorber2.getTextFragments()) {
                String phone = textFragment.getText();
                if(isBlank(phone)){
                    continue;
                }
                textFragment.setText("***********");
                textFragment.getTextState().setBackgroundColor(com.aspose.pdf.Color.getWhite());
            }
            pdfDoc.save(targetPath);
        }catch(Exception ex){
            System.err.println("PDF ERROR:   file operation error,file path is  "+srcPath+"   error is:  "+ex.getMessage());
            return;
        }
    }

}
