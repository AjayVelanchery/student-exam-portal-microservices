package com.example.student_portal.student_service.csv;

import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentExcelParser {

    public List<StudentPreRegisterRequest> parse(MultipartFile file) {

        List<StudentPreRegisterRequest> students = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                StudentPreRegisterRequest req = new StudentPreRegisterRequest();

                req.setCapId(row.getCell(0).getStringCellValue());
                req.setName(row.getCell(1).getStringCellValue());


                Cell dobCell = row.getCell(2);
                if (dobCell == null) {
                    throw new RuntimeException("DOB missing at row " + (i + 1));
                }

                if (dobCell.getCellType() == CellType.NUMERIC
                        && DateUtil.isCellDateFormatted(dobCell)) {
                    req.setDob(dobCell.getLocalDateTimeCellValue().toLocalDate());
                } else if (dobCell.getCellType() == CellType.STRING) {
                    req.setDob(LocalDate.parse(dobCell.getStringCellValue()));
                } else {
                    throw new RuntimeException("Invalid DOB format at row " + (i + 1));
                }

                req.setGender(row.getCell(3).getStringCellValue());
                req.setDistrict(row.getCell(4).getStringCellValue());
                req.setState(row.getCell(5).getStringCellValue());
                req.setPostalCode(row.getCell(6).getStringCellValue());
                req.setEmail(row.getCell(7).getStringCellValue());
                req.setMobile(row.getCell(8).getStringCellValue());
                req.setParentName(row.getCell(9).getStringCellValue());
                req.setParentContactNo(row.getCell(10).getStringCellValue());
                req.setProgramme(row.getCell(11).getStringCellValue());
                req.setBatch(row.getCell(12).getStringCellValue());
                req.setBloodGroup(row.getCell(13).getStringCellValue());
                req.setCommunity(row.getCell(14).getStringCellValue());
                req.setAdhaarNumber(row.getCell(15).getStringCellValue());

                students.add(req);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Excel file", e);
        }

        return students;
    }
}
