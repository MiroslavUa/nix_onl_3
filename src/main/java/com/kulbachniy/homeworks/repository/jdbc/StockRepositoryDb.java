package com.kulbachniy.homeworks.repository.jdbc;

import com.kulbachniy.homeworks.config.JdbcConfig;
import com.kulbachniy.homeworks.model.derivative.DerivativeType;
import com.kulbachniy.homeworks.model.derivative.Exchange;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.repository.CrudRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StockRepositoryDb implements CrudRepository<Stock> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockRepositoryDb.class);

    private final Connection connection = JdbcConfig.getConnection();

    private static StockRepositoryDb instance;

    private StockRepositoryDb() {
        LOGGER.info("Stock Repository have been created");
    }

    public static StockRepositoryDb getInstance(){
        if(instance == null) {
            instance = new StockRepositoryDb();
        }
        return instance;
    }

    @Override
    @SneakyThrows
    public void save(Stock derivative) {
        String insertDerivative = """
            INSERT INTO public.derivative( id, ticker, instrument, exch, price)
            VALUES (?, ?, ?::derivativetype, ?::exchange, ?);""";

        String insertStock = """
                INSERT INTO public.stock(
                	id, companyname, industry, volume, atr, date)
                	VALUES (?, ?, ?, ?, ?, ?::timestamp);""";

        connection.setAutoCommit(false);

        try(final PreparedStatement preparedDerivative = connection.prepareStatement(insertDerivative);
            final PreparedStatement preparedStock = connection.prepareStatement(insertStock)){
            preparedDerivative.setString(1, derivative.getId());
            preparedDerivative.setString(2, derivative.getTicker());
            preparedDerivative.setString(3, String.valueOf(derivative.getType()));
            preparedDerivative.setString(4, String.valueOf(derivative.getExchange()));
            preparedDerivative.setDouble(5, derivative.getPrice());
            preparedDerivative.addBatch();
            preparedDerivative.executeBatch();

            preparedStock.setString(1, derivative.getId());
            preparedStock.setString(2, derivative.getCompanyName());
            preparedStock.setString(3, derivative.getIndustry());
            preparedStock.setDouble(4, derivative.getVolume());
            preparedStock.setDouble(5, derivative.getAverageTrueRange());
            preparedStock.setString(6, derivative.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            preparedStock.addBatch();
            preparedStock.executeBatch();

            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    @Override
    @SneakyThrows
    public void saveAll(List<Stock> derivatives) {
        String insertDerivative = """
            INSERT INTO public.derivative( id, ticker, instrument, exch, price)
            VALUES (?, ?, ?::derivativetype, ?::exchange, ?);""";

        String insertStock = """
                INSERT INTO public.stock(
                	id, companyname, industry, volume, atr, date)
                	VALUES (?, ?, ?, ?, ?, ?::timestamp);""";

        connection.setAutoCommit(false);

        try(final PreparedStatement preparedDerivative = connection.prepareStatement(insertDerivative);
            final PreparedStatement preparedStock = connection.prepareStatement(insertStock)){
            for(Stock derivative : derivatives) {
                preparedDerivative.setString(1, derivative.getId());
                preparedDerivative.setString(2, derivative.getTicker());
                preparedDerivative.setString(3, String.valueOf(derivative.getType()));
                preparedDerivative.setString(4, String.valueOf(derivative.getExchange()));
                preparedDerivative.setDouble(5, derivative.getPrice());
                preparedDerivative.addBatch();
                preparedDerivative.executeBatch();

                preparedStock.setString(1, derivative.getId());
                preparedStock.setString(2, derivative.getCompanyName());
                preparedStock.setString(3, derivative.getIndustry());
                preparedStock.setDouble(4, derivative.getVolume());
                preparedStock.setDouble(5, derivative.getAverageTrueRange());
                preparedStock.setString(6, derivative.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                preparedStock.addBatch();
                preparedStock.executeBatch();
            }

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        }
        connection.setAutoCommit(true);
    }

    @Override
    @SneakyThrows
    public boolean update(Stock derivative) {
        String updateDerivative = """
               UPDATE public.derivative
               SET ticker=?, instrument=?::derivativetype, exch=?::exchange, price=?
               WHERE derivative.id = ?""";

        String updateStock = """
                UPDATE public.stock
                SET companyname=?, industry=?, volume=?, atr=?, date=?::timestamp
                WHERE stock.id = ?;""";

        connection.setAutoCommit(false);

        try(final PreparedStatement preparedDerivative = connection.prepareStatement(updateDerivative);
            final PreparedStatement preparedStock = connection.prepareStatement(updateStock)){
            preparedDerivative.setString(1, derivative.getTicker());
            preparedDerivative.setString(2, String.valueOf(derivative.getType()));
            preparedDerivative.setString(3, String.valueOf(derivative.getExchange()));
            preparedDerivative.setDouble(4, derivative.getPrice());
            preparedDerivative.setString(5, derivative.getId());
            preparedDerivative.addBatch();
            preparedDerivative.executeBatch();

            preparedStock.setString(1, derivative.getCompanyName());
            preparedStock.setString(2, derivative.getIndustry());
            preparedStock.setDouble(3, derivative.getVolume());
            preparedStock.setDouble(4, derivative.getAverageTrueRange());
            preparedStock.setString(5, derivative.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            preparedStock.setString(6, derivative.getId());
            preparedStock.addBatch();
            preparedStock.executeBatch();

            connection.commit();
            connection.setAutoCommit(true);

            return true;
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(String id) {
        String deleteStock = """
                DELETE FROM public.stock
                WHERE id = ?;""";

        String deleteDerivative = """
                DELETE FROM public.derivative
                WHERE id = ?;""";

        try(final PreparedStatement preparedDerivative = connection.prepareStatement(deleteDerivative);
            final PreparedStatement preparedStock = connection.prepareStatement(deleteStock)){
            preparedStock.setString(1, id);
            preparedDerivative.setString(1, id);

            return (preparedStock.executeUpdate() > 0) && (preparedDerivative.executeUpdate() > 0);
        }
    }

    @Override
    @SneakyThrows
    public Stock findById(String id) {
        String findStockById = """
            SELECT * FROM derivative
            RIGHT OUTER JOIN stock ON derivative.id = stock.id
            WHERE stock.id = ?;""";

        try(PreparedStatement preparedStock = connection.prepareStatement(findStockById)){
            preparedStock.setString(1, id);
            ResultSet resultSet = preparedStock.executeQuery();
            while(resultSet.next()){
                String ticker = resultSet.getString("ticker");
                DerivativeType type = DerivativeType.valueOf(resultSet.getString("instrument"));
                Exchange exchange = Exchange.valueOf(resultSet.getString("exch"));
                double price = resultSet.getDouble("price");
                String companyName = resultSet.getString("companyname");
                String industry = resultSet.getString("industry");
                double volume = resultSet.getDouble("volume");
                double atr = resultSet.getDouble("atr");
                LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getString("date"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                Stock stock =  new Stock(ticker, exchange, price, companyName, industry, volume, atr, localDateTime, Collections.emptyList());
                stock.setId(id);
                return stock;
            }
        }
        return null;
    }

    @Override
    @SneakyThrows
    public Stock findByTicker(String ticker) {
        String findStockByTicker = """
            SELECT * FROM derivative
            RIGHT OUTER JOIN stock ON derivative.id = stock.id
            WHERE derivative.ticker = ?;""";

        try(PreparedStatement preparedStock = connection.prepareStatement(findStockByTicker)){
            preparedStock.setString(1, ticker);
            ResultSet resultSet = preparedStock.executeQuery();
            while(resultSet.next()){
                String id = resultSet.getString("id");
                DerivativeType type = DerivativeType.valueOf(resultSet.getString("instrument"));
                Exchange exchange = Exchange.valueOf(resultSet.getString("exch"));
                double price = resultSet.getDouble("price");
                String companyName = resultSet.getString("companyname");
                String industry = resultSet.getString("industry");
                double volume = resultSet.getDouble("volume");
                double atr = resultSet.getDouble("atr");
                LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getString("date"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                Stock stock =  new Stock(ticker, exchange, price, companyName, industry, volume, atr, localDateTime, Collections.emptyList());
                stock.setId(id);
                return stock;
            }
        }
        return null;
    }

    @Override
    @SneakyThrows
    public List<Stock> getAll() {
        String getAllStock = """
            SELECT * FROM derivative
            RIGHT OUTER JOIN stock ON derivative.id = stock.id;""";

        List<Stock> stockList = new ArrayList<>();

        try(PreparedStatement preparedFutures = connection.prepareStatement(getAllStock)){
            ResultSet resultSet = preparedFutures.executeQuery();
            while(resultSet.next()){
                String id = resultSet.getString("id");
                String ticker = resultSet.getString("ticker");
                DerivativeType type = DerivativeType.valueOf(resultSet.getString("instrument"));
                Exchange exchange = Exchange.valueOf(resultSet.getString("exch"));
                double price = resultSet.getDouble("price");
                String companyName = resultSet.getString("companyname");
                String industry = resultSet.getString("industry");
                double volume = resultSet.getDouble("volume");
                double atr = resultSet.getDouble("atr");
                LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getString("date"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                Stock stock =  new Stock(ticker, exchange, price, companyName, industry, volume, atr, localDateTime, Collections.emptyList());
                stock.setId(id);
                stockList.add(stock);
            }
            return stockList;
        }
    }
}
