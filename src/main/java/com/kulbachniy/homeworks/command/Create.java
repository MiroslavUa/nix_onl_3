package com.kulbachniy.homeworks.command;

import com.kulbachniy.homeworks.model.derivative.DerivativeType;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.service.DerivativeFactory;
import com.kulbachniy.homeworks.service.DerivativeService;
import com.kulbachniy.homeworks.service.StockService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Create implements Command{
    private static final DerivativeService<Stock> STOCK_DERIVATIVE_SERVICE = StockService.getInstance();

    @Override
    public void execute() throws IOException {
        System.out.println("Choose a derivative to be added:");
        final DerivativeType[] types = DerivativeType.values();
        final List<String> names = getNamesOfType(types);
        final int type = UserInputUtil.intValue();
        final String ticker = UserInputUtil.stringValue();
        STOCK_DERIVATIVE_SERVICE.save((Stock) DerivativeFactory.createDerivative(types[type], ticker));
    }

    private List<String> getNamesOfType(final DerivativeType[] values){
        final List<String> names = new ArrayList<>(values.length);
        for(DerivativeType dt : values){
            names.add(dt.name());
        }
        return names;
    }
}
