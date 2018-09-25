import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liushichang on 2018/9/21.
 */
public class MainMethod  {

    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();
        System.out.println("............replace  start............");
        List<String> filePaths=getFilePathByArgs(args);
        if(filePaths==null||filePaths.size()==0){
            return;
        }
        String targetPath=args[1];
        System.out.println("support file count is "+filePaths.size());
        for (int i=0;i<filePaths.size();i++) {
            String inputFilePath=filePaths.get(i);
            if(AbstractUtils.isBlank(inputFilePath)){
                continue;
            }
            String isFile=AbstractUtils.getFileExtName(inputFilePath);
            if(AbstractUtils.isBlank(isFile)){
                continue;
            }
            try{
                String [] pathArray=inputFilePath.split("\\/");
                String name=pathArray[pathArray.length-1];
                System.out.println("count "+i+" start, file name: "+name);
                String ext=AbstractUtils.getFileExtName(name).toLowerCase();
                if(AbstractUtils.DOCX.equals(ext)|AbstractUtils.DOC.equals(ext)){
                    WordUtils.replace(inputFilePath,targetPath+"/"+name);
                    System.out.println(name+" end;");
                    continue;
                }
                if(AbstractUtils.PDF.equals(ext)){
                    PdfUtils.replace(inputFilePath,targetPath+"/"+name);
                    System.out.println(name+" end;");
                    continue;
                }
                System.err.println("not support file type :"+ext);
            }catch(Exception ex){
                System.err.println("error, file path is "+inputFilePath);
                ex.printStackTrace();
                continue;
            }
        }
        System.out.println("............replace  done............");
        System.out.println("cost: "+((System.currentTimeMillis()-startTime)/1000)+"s");
    }

    private static List<String> getFilePathByArgs(String[] args) {
        if(args==null){
            System.out.println("error:     args is null");
            return null;
        }
        if(args.length!=2||AbstractUtils.isBlank(args[0])||AbstractUtils.isBlank(args[1])){
            System.err.println("error:     args is error");
            return null;
        }
        File srcDicFile=new File(args[0]);
        File targetFile=new File(args[1]);
        if (srcDicFile==null|targetFile==null){
            System.err.println("error:     directory is not exist");
            return null;
        }
        if(!targetFile.exists()){
            targetFile.mkdir();
        }
        String srcPath=args[0];
        List<String> filePaths=new ArrayList<>();
        if(srcDicFile.isDirectory()){
            String [] fileNames=srcDicFile.list();
            if(fileNames==null||fileNames.length<=0){
                System.err.println("error:     directory is not file exist");
                return null;
            }
            for (String fileName : fileNames) {
                if(AbstractUtils.isBlank(fileName)){
                    continue;
                }
                String ext=AbstractUtils.getFileExtName(fileName);
                if(AbstractUtils.PDF.equals(ext)|AbstractUtils.DOC.equals(ext)|AbstractUtils.DOCX.equals(ext)){
                    String filePath=srcPath+"/"+fileName;
                    filePaths.add(filePath);
                    continue;
                }
                System.err.println("only doc or docx,nonsupport type "+ fileName);
            }
        }else{
            filePaths.add(srcPath);
        }
        return filePaths;
    }

}
