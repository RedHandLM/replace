import com.aspose.words.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Created by liushichang on 2018/9/21.
 */
public class WordUtils extends AbstractUtils {

    public static void replace(String srcPath, String targetPath){
        getLicense();
        try (
                FileOutputStream os = new FileOutputStream(new File(targetPath));
                FileInputStream inputStream= new FileInputStream(new File(srcPath))
        ) {
            com.aspose.words.Document doc = new com.aspose.words.Document(inputStream);
            Range range = doc.getRange();
            SectionCollection sections = doc.getSections();
            Iterator<Section> iterator = sections.iterator();
            while (iterator.hasNext()) {
                Section next = iterator.next();
                TableCollection tables = next.getBody().getTables();
                Iterator<com.aspose.words.Table> tableIt = tables.iterator();
                while (tableIt.hasNext()) {
                    Table table = tableIt.next();
                    table.setBorders(0, 0, java.awt.Color.white);
                }
            }
            range.replace(Pattern.compile("\\d{11}"), "***********");
            range.replace(Pattern.compile("\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}"), "*********");
            WordML2003SaveOptions o = new WordML2003SaveOptions();
            o.setPrettyFormat(true);
            o.setUseHighQualityRendering(false);
            o.setColorMode(0);
            String ext = getFileExtName(srcPath);
            doc.save(os, AbstractUtils.DOC.equals(ext) ? SaveFormat.DOC : SaveFormat.DOCX);
        }catch(Exception ex){
            System.err.println("WORD ERROR:   file operation error,file path is  "+srcPath+"   error is:  "+ex.getMessage());
            return;
        }
    }

}
