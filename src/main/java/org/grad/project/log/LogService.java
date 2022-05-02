package org.grad.project.log;

import org.grad.project.employee.EmployeeRepository;
import org.grad.project.model.Entry;
import org.grad.project.model.Log;
import org.grad.project.system.Repository;
import org.grad.project.system.Table;
import org.grad.project.visitor.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

@Service
public class LogService {

    private final EmployeeRepository employeeRepository;
    private final VisitorRepository visitorRepository;

    @Autowired
    public LogService(EmployeeRepository employeeRepository, VisitorRepository visitorRepository) {
        this.employeeRepository = employeeRepository;
        this.visitorRepository = visitorRepository;
    }

    public void join(Log log) {
        IO.getInstance().write(log);
    }

    public boolean checkInfo(Map<String, String> info) {

        boolean ret = false;

        int id = Integer.parseInt(info.get("ID"));
        String phone = info.get("phone");
        int address = Integer.parseInt(info.get("address"));

        switch (first(id)) {
            case 1: {
                Optional<Entry> optional = employeeRepository.findById(id);
                if (optional.isPresent()) {
                    Entry employee = optional.get();
                    ret = employee.compare(phone, Table.codeToAddress(address));
                }
                break;
            } case 2: {
                Optional<Entry> optional = visitorRepository.findById(id);
                if (optional.isPresent()) {
                    Entry visitor = optional.get();
                    ret = visitor.compare(phone, Table.codeToAddress(address));
                }
                break;
            } default: { }
        }

        return ret;
    }

    public boolean checkID(int id) {

        Repository repo;

        switch (first(id)) {
            case 1: {
                repo = employeeRepository;
                break;
            } case 2: {
                repo = visitorRepository;
                break;
            } default: return false;
        }

        return repo.findById(id).isPresent();

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

    private int first(int id) {
        return id / 100000;
    }
}
