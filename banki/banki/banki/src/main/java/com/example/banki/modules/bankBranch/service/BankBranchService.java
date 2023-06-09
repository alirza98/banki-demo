package com.example.banki.modules.bankBranch.service;

import com.example.banki.modules.bankBranch.BankBranchConvertor;
import com.example.banki.modules.bankBranch.model.BankBranch;
import com.example.banki.modules.bankBranch.repository.BankBranchRepository;
import com.example.banki.modules.employee.model.Employee;
import com.example.banki.modules.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankBranchService {

    private final BankBranchRepository bankBranchRepository;
    private final EmployeeRepository employeeRepository;
    private final BankBranchConvertor bankBranchConvertor;

    public BankBranchService(BankBranchRepository bankBranchRepository, EmployeeRepository employeeRepository, BankBranchConvertor bankBranchConvertor) {
        this.bankBranchRepository = bankBranchRepository;
        this.employeeRepository = employeeRepository;
        this.bankBranchConvertor = bankBranchConvertor;
    }

    public String save(BankBranch bankBranch) {
        bankBranchRepository.save(bankBranch);
        return bankBranchConvertor.dtoForSave(bankBranch);
    }

    public void delete(int id) {
        bankBranchRepository.deleteById(id);
    }

    public BankBranch addEmpToBank(int bankId, int empId) {
        BankBranch addemp = bankBranchRepository.findById(bankId).get();
        Employee entity = employeeRepository.findById(empId).get();
        addemp.addEmployee(entity);
        return bankBranchRepository.save(addemp);
    }

    public List<Employee> getEmployeesByBankId(int id){
        BankBranch bankBranch = bankBranchRepository.findById(id).orElseThrow(()->new RuntimeException("Id is not available"));
        List<Employee> employeeList = new ArrayList<>();
        employeeList.addAll(bankBranch.getEmployees());
        return employeeList;
    }

}
