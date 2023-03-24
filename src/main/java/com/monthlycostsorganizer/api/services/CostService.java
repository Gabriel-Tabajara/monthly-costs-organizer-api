package com.monthlycostsorganizer.api.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monthlycostsorganizer.api.models.entitys.Cost;
import com.monthlycostsorganizer.api.models.entitys.MonthYear;
import com.monthlycostsorganizer.api.repositories.CostRepository;

@Service
@Transactional
public class CostService {
    
    private CostRepository costRep;

    @Autowired
    public CostService(CostRepository costRep) {
        this.costRep = costRep;
    }

    public void addCost(Cost cost, Date date) {
        String monthyear = MonthYear.dateToMonthYear(date);
        this.costRep.postCostDB(cost, monthyear);
    }
    
    public ArrayList<Cost> getCostsByMonth(String month, String year) {
        try {
            String mthYrId = MonthYear.dateToMonthYear(month, year);
    
            CompletableFuture<ArrayList<Cost>> future = this.costRep.getCostsByMonthDB(mthYrId); 
            ArrayList<Cost> costsFromMonth = future.get();
    
            return costsFromMonth;
        } catch (Exception e) {
            throw new Error("Error");
        }
    }
}
