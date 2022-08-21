package com.kulbachniy.homeworks.service;

import com.kulbachniy.homeworks.annotation.Autowired;
import com.kulbachniy.homeworks.annotation.Singleton;
import com.kulbachniy.homeworks.model.derivative.Exchange;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Singleton
public class StockServiceParser extends DerivativeService<Stock> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceParser.class);

    private static StockServiceParser instance;

    @Autowired
    public StockServiceParser(StockRepository repository){
        super(repository);
    }

    public static StockServiceParser getInstance(){
        if(instance == null) {
            instance = new StockServiceParser(StockRepository.getInstance());
        }
        return instance;
    }
    private final Function<Map<String, Object>, Stock> createStock = hashMap -> new Stock (
                (String) hashMap.get("Ticker"),
                (Exchange) hashMap.get("Exchange"),
                (Double) hashMap.get("Price"),
                (String) hashMap.get("Company Name"),
                (String) hashMap.get("Industry"),
                (Double) hashMap.get("Volume"),
                (Double) hashMap.get("ATR"),
                (LocalDateTime) hashMap.get("Time"),
                (List<String>) hashMap.get("Production"));

    private Stock createStockFromMap(Map<String, Object> hashMap) {
        return createStock.apply(hashMap);
    }

    private Optional<Stock> createStock(String fileName, String valueRegex, String keyRegex){
        List<String> production = new ArrayList<>();
        Map<String, Object> hashMap = new HashMap<>();
        String foundValue;
        String foundKey = null;

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try(InputStream in = loader.getResourceAsStream(fileName)) {
            assert in != null;
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){

                List<String> lines = new ArrayList<>();
                while(reader.ready()) {
                    lines.add(reader.readLine());
                }

                for (String l : lines){
                    Pattern patternValue = Pattern.compile(valueRegex);
                    Matcher matcherValue = patternValue.matcher(l);

                    if(matcherValue.find()){
                        foundValue = matcherValue.group(1);

                        Pattern patternKey = Pattern.compile(keyRegex);
                        Matcher matcherKey = patternKey.matcher(l);
                        if(matcherKey.find()) {
                            foundKey = matcherKey.group(1);
                        }

                        if (foundKey.contains("Price")) {
                            foundKey = "Price";
                        }

                        switch (foundKey) {
                            case "Ticker", "Company", "Industry" -> {
                                hashMap.put(foundKey, foundValue);
                            }
                            case "Exchange" -> {
                                hashMap.put(foundKey, Exchange.valueOf(foundValue.toUpperCase()));
                            }
                            case "Price", "Volume", "ATR" -> {
                                hashMap.put(foundKey, Double.parseDouble(foundValue));
                            }
                            case "Time" -> {
                                hashMap.put(foundKey,
                                        LocalDateTime.parse(foundValue, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
                            }
                            default -> {
                                production.add(foundValue);
                            }
                        }
                    }
                }

                Stock stock = createStockFromMap(hashMap);
                stock.setProduction(production);
                return Optional.of(stock);

            }
        } catch (IOException ex){
            System.out.println(ex.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Stock> createStockFromJson(String jsonFileName){
        String valueRegexJson = "\".+\": \"(.*?)\".*";
        String keyRegexJson = "\"(.*?)\".*";
        return createStock(jsonFileName, valueRegexJson, keyRegexJson);
    }

    public Optional<Stock> createStockFromXml(String xmlFileName){
        String valueRegexXml = ".*?>(.*?)<";
        String keyRegexXml = "<(.*?)>.";
        return createStock(xmlFileName, valueRegexXml, keyRegexXml);
    }
}
