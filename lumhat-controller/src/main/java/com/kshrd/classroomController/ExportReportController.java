package com.kshrd.classroomController;

import com.gembox.spreadsheet.*;
import com.kshrd.model.User;
import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.model.classroom.ClassroomResult;
import com.kshrd.service.classroom.history.teacher.ClassroomHistoryTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

@Controller
public class ExportReportController {

    @Value("${file.excel.path}")
    String path;

    private ClassroomHistoryTeacherService classroomHistoryTeacherService;

    @Autowired
    public void setClassroomClassService(ClassroomHistoryTeacherService ClassroomHistoryTeacherService){
        this.classroomHistoryTeacherService = ClassroomHistoryTeacherService;
    }



    static {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
    }

    // calculate rank
    private List<ClassroomResult> getRank(List<ClassroomResult> list){
        list.sort(Comparator.comparing(ClassroomResult::getScore).reversed());
        return list;
    }

    @RequestMapping(value = "/classroom/export/{id}/{topic}",method = RequestMethod.POST,produces = { "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" })
    public HttpEntity<byte[]> create(@PathVariable("id") Integer id,@PathVariable("topic") String topic) throws IOException{

        SaveOptions options = getSaveOptions("XLSX");
        ExcelFile excelFile = ExcelFile.load(path+"excel/report-sample.xlsx");
        ExcelWorksheet sheet = excelFile.addWorksheet("Student Report");
        excelFile.removeWorksheet(1);
        SheetHeaderFooter headerFooter = sheet.getHeadersFooters();

//         Show logo
        headerFooter.getFirstPage().getHeader().getLeftSection()
                .appendPicture(path+"images/favicon-b.png",
                        100, 50);
        headerFooter.getDefaultPage().getHeader().setLeftSection(headerFooter.getFirstPage().getHeader().getLeftSection());

        // "Page number" of "Number of pages"
        headerFooter.getFirstPage().getFooter().getRightSection().append("Page ").append(HeaderFooterFieldType.PAGE_NUMBER)
                .append(" of ").append(HeaderFooterFieldType.NUMBER_OF_PAGES);
        headerFooter.getDefaultPage().setFooter(headerFooter.getFirstPage().getFooter());

        sheet = excelFile.getWorksheet(0);


        ClassroomClass classroomClass = classroomHistoryTeacherService.findClassInfoByQuizId(id);

        sheet.getCell("B3").setValue(classroomClass.getName());
        sheet.getCell("B4").setValue(topic);
        sheet.getCell("B5").setValue(classroomClass.getRoom());
        sheet.getCell("B6").setValue(classroomClass.getTeacherFirstName()+" "+classroomClass.getTeacherLastName());
        sheet.getCell("B7").setValue(new SimpleDateFormat("dd/MMMM/yyyy").format(classroomClass.getCreatedDate()));



        List<ClassroomResult> classList = getRank(classroomHistoryTeacherService.findResultByQuizId(id));
        sheet.getCell("B8").setValue(classList.size());

        int rank = 1, index = 12;

        for(int row=1; row<=classList.size(); row++){
            ClassroomResult item = classList.get(row-1);

            if(classList.get(0).getScore() != item.getScore())
                rank++;

            sheet.getCell("A"+index).setValue(row);
            sheet.getCell("B"+index).setValue(item.getFirstName()+" "+item.getLastName());
            sheet.getCell("C"+index).setValue(item.getScore()+"/"+item.getFullScore());
            sheet.getCell("D"+index).setValue(rank);

            formatTable(sheet,"A"+index);
            formatTable(sheet,"B"+index);
            formatTable(sheet,"C"+index);
            formatTable(sheet,"D"+index);

            index++;
        }

        String currentDate = new SimpleDateFormat("dd-MMMM-yyyy-hh-mm").format(new Date());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        try {
            user = (User) auth.getPrincipal();
        } catch (Exception e) {
            user = new User();
        }
        String className = classroomClass.getName()+"_class";
        String filename =  user.getFirstName()+"_"+user.getLastName()+"-"+ className + "-" +currentDate +"-report.";

        byte[] bytes = getBytes(excelFile, options);

        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.CONTENT_TYPE, options.getContentType());
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename+ "XLSX".toLowerCase());
        header.setContentLength(bytes.length);

        return new HttpEntity<>(bytes, header);

    }

    // format table
    private void formatTable(ExcelWorksheet sheet, String cellName){
        CellStyle style = sheet.getCell(cellName).getStyle();
        style.setHorizontalAlignment(HorizontalAlignmentStyle.LEFT);
        style.setVerticalAlignment(VerticalAlignmentStyle.CENTER);
        sheet.getColumn(0).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.LEFT);
        sheet.getColumn(1).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.LEFT);
        sheet.getColumn(2).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.LEFT);
        sheet.getColumn(3).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.LEFT);
        style.getBorders().setBorders(EnumSet.of(MultipleBorders.INSIDE_HORIZONTAL, MultipleBorders.INSIDE_VERTICAL,
                MultipleBorders.TOP, MultipleBorders.BOTTOM, MultipleBorders.LEFT, MultipleBorders.RIGHT),
                SpreadsheetColor.fromColor(Color.BLACK), LineStyle.THIN);
    }

    private byte[] getBytes(ExcelFile book, SaveOptions options) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        book.save(outputStream, options);
        return outputStream.toByteArray();
    }

    private static SaveOptions getSaveOptions(String format) {
        switch (format.toUpperCase()) {
            case "XLSX":
                return SaveOptions.getXlsxDefault();
            case "XLS":
                return SaveOptions.getXlsDefault();
            case "ODS":
                return SaveOptions.getOdsDefault();
            case "CSV":
                return SaveOptions.getCsvDefault();
            case "HTML":
                return SaveOptions.getHtmlDefault();
            default:
                throw new IllegalArgumentException("Format '" + format + "' is not supported.");
        }
    }
}
