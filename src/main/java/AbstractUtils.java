import com.aspose.pdf.License;

import java.io.InputStream;

/**
 * Created by liushichang on 2018/9/21.
 */
public abstract class AbstractUtils {

    public static final String PDF="pdf";
    public static final String DOCX="docx";
    public static final String DOC="doc";


    protected static boolean isNotBlank(String s) {
        return s != null && s.trim().length() > 0;
    }

    protected static boolean isBlank(String s) {
        return !isNotBlank(s);
    }

    public static String getFileExtName(String fileName) {
        if (fileName == null) {
            return "";
        } else {
            int lastIndex = fileName.lastIndexOf(".");
            return (lastIndex == -1) ? "" : fileName.substring(lastIndex + 1).toLowerCase();
        }
    }


    public static void setLicense(){
        InputStream license = AbstractUtils.class.getResourceAsStream("license.xml");
        try {
            new License().setLicense(license);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = AbstractUtils.class.getResourceAsStream("w/license.xml");
            com.aspose.words.License aposeLic = new com.aspose.words.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
