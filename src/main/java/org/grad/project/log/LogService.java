package org.grad.project.log;

import org.grad.project.employee.EmployeeRepository;
import org.grad.project.model.Log;
import org.grad.project.model.LogForm;
import org.grad.project.system.Repository;
import org.grad.project.visitor.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

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
        IO.getInstance().write(log, checkID(log.getId()), checkTemp(log.getTemp()));
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

    public boolean checkID(int id) {
        if (first(id) > 2) return false;
        Repository repo = first(id) == 1 ? employeeRepository : visitorRepository;
        return repo.findById(id).isPresent();
    }

    public boolean checkTemp(float temp) {
        return temp >= 35 && temp <= 38;
    }

    public Log makeLog(LogForm form) {
        return new Log(form, checkID(form.getId()), checkTemp(form.getTemp()));
    }
}
