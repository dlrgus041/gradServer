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
        log.setCode(checkLog(log));
        IO.getInstance().write(log);
    }

    public int checkInfo(Map<String, String> info) {

        int ret;

        int id = Integer.parseInt(info.get("ID"));
        String phone = info.get("phone");
        int address = Integer.parseInt(info.get("address"));

        if (first(id) > 2) ret = Log.DATA_DOES_NOT_EXIST;
        else {

            if (decrypt(id)) {

                Repository repo = first(id) == 1 ? employeeRepository : visitorRepository;
                Optional<Entry> optional = repo.findById(id);

                if (optional.isPresent()) {
                    Entry entry = optional.get();
                    ret = entry.compare(phone, Table.codeToAddress(address)) ? Log.PASS : Log.INFO_DOES_NOT_MATCH;
                } else ret = Log.DATA_DOES_NOT_EXIST;

            } else ret = Log.DATA_DOES_NOT_EXIST;
        }

        if (ret == Log.PASS) {
            float temp = Float.parseFloat(info.get("temp"));
            if (temp < 35 || temp > 38) ret = Log.TEMP_OUT_OF_RANGE;
        }

        return ret;
    }

    public int checkLog(Log log) {

        Repository repo;

        switch (first(log.getId())) {
            case 1: {
                repo = employeeRepository;
                break;
            } case 2: {
                repo = visitorRepository;
                break;
            } default: return Log.DATA_DOES_NOT_EXIST;
        }

        if (repo.findById(log.getId()).isPresent()) return log.checkTemp() ? Log.PASS : Log.TEMP_OUT_OF_RANGE;

        return Log.DATA_DOES_NOT_EXIST;

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

    private boolean decrypt(int id) {

        int sum = 0;

        for (int i = 1; i <= 6; i++) {
            sum += i * (id % 10);
            id /= 10;
        }

        return sum % 7 == 0;
    }
}
