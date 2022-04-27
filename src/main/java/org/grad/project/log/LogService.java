package org.grad.project.log;

import org.grad.project.employee.EmployeeRepository;
import org.grad.project.entry.Entry;
import org.grad.project.system.Util;
import org.grad.project.visitor.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LogService {

    private final EmployeeRepository employeeRepository;
    private final VisitorRepository visitorRepository;

    @Autowired
    public LogService(EmployeeRepository employeeRepository, VisitorRepository visitorRepository) {
        this.employeeRepository = employeeRepository;
        this.visitorRepository = visitorRepository;
    }

    public boolean check(Map<String, Object> info) {

        boolean ret = false;

        int id = (int) info.get("ID");
        String phone = info.get("phone").toString();
        int address = (int) info.get("address");

        switch (id / 100000) {
            case 1: {
                Optional<Entry> optional = employeeRepository.findById(id);
                if (optional.isPresent()) {
                    Entry employee = optional.get();
                    ret = employee.compare(phone, Util.codeToAddress(address));
                }
            } case 2: {
                Optional<Entry> optional = visitorRepository.findById(id);
                if (optional.isPresent()) {
                    Entry visitor = optional.get();
                    ret = visitor.compare(phone, Util.codeToAddress(address));
                }
            } default: { }
        }

        return ret;
    }

    public List<Log> read() throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("log.txt"));
        List<Log> ret = new LinkedList<>();

        while (true) {
            String line = br.readLine();
            if (line == null) break;
            ret.add(new Log(line));
        }

        br.close();
        return ret;
    }
}
