package com.example.student_portal.student_service.csv;

import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentCSVParser {

    public List<StudentPreRegisterRequest> parse(MultipartFile file) throws IOException {

        List<StudentPreRegisterRequest> students = new ArrayList<>();

        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim())) {

            for (CSVRecord record : csvParser) {

                StudentPreRegisterRequest req = new StudentPreRegisterRequest();
                req.setCapId(record.get("capId"));
                req.setName(record.get("name"));
                req.setDob(LocalDate.parse(record.get("dob")));
                req.setGender(record.get("gender"));
                req.setDistrict(record.get("district"));
                req.setState(record.get("state"));
                req.setPostalCode(record.get("postalCode"));
                req.setEmail(record.get("email"));
                req.setMobile(record.get("mobile"));
                req.setParentName(record.get("parentName"));
                req.setParentContactNo(record.get("parentContactNo"));
                req.setProgramme(record.get("programme"));
                req.setBatch(record.get("batch"));
                req.setBloodGroup(record.get("bloodGroup"));
                req.setCommunity(record.get("community"));
                req.setAdhaarNumber(record.get("adhaarNumber"));

                students.add(req);
            }
        }

        return students;
    }
}
