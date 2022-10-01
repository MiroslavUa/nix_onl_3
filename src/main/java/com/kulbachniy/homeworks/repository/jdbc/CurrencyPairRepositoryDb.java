package com.kulbachniy.homeworks.repository.jdbc;

import com.kulbachniy.homeworks.config.JdbcConfig;
import com.kulbachniy.homeworks.model.derivative.CurrencyPair;
import com.kulbachniy.homeworks.model.derivative.DerivativeType;
import com.kulbachniy.homeworks.model.derivative.Exchange;
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
import java.util.List;

public class CurrencyPairRepositoryDb implements CrudRepository<CurrencyPair> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyPairRepositoryDb.class);

    private final Connection connection = JdbcConfig.getConnection();

    private static CurrencyPairRepositoryDb instance;

    private CurrencyPairRepositoryDb() {
        LOGGER.info("Currency Pairs Repository have been created");
    }

    public static CurrencyPairRepositoryDb getInstance(){
        if(instance == null) {
            instance = new CurrencyPairRepositoryDb();
        }
        return instance;
    }

    @Override
    @SneakyThrows
    public void save(CurrencyPair derivative) {
        String insertDerivative = """
            INSERT INTO public.derivative( id, ticker, instrument, exch, price)
            VALUES (?, ?, ?::derivativetype, ?::exchange, ?);""";

        String insertPair = """
                INSERT INTO public.currencypair(
                	id, basecurrency, quotecurrency, date)
                	VALUES (?, ?, ?, ?::timestamp);""";

        connection.setAutoCommit(false);

        try(final PreparedStatement preparedDerivative = connection.prepareStatement(insertDerivative);
            final PreparedStatement preparedPair = connection.prepareStatement(insertPair)){
            preparedDerivative.setString(1, derivative.getId());
            preparedDerivative.setString(2, derivative.getTicker());
            preparedDerivative.setString(3, String.valueOf(derivative.getType()));
            preparedDerivative.setString(4, String.valueOf(derivative.getExchange()));
            preparedDerivative.setDouble(5, derivative.getPrice());
            preparedDerivative.addBatch();
            preparedDerivative.executeBatch();

            preparedPair.setString(1, derivative.getId());
            preparedPair.setString(2, derivative.getBaseCurrency());
            preparedPair.setString(3, derivative.getQuoteCurrency());
            preparedPair.setString(4, derivative.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            preparedPair.addBatch();
            preparedPair.executeBatch();

            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    @Override
    @SneakyThrows
    public void saveAll(List<CurrencyPair> derivatives) {
        String insertDerivative = """
            INSERT INTO public.derivative( id, ticker, instrument, exch, price)
            VALUES (?, ?, ?::derivativetype, ?::exchange, ?);""";

        String insertPair = """
                INSERT INTO public.currencypair(
                	id, basecurrency, quotecurrency, date)
                	VALUES (?, ?, ?, ?::timestamp);""";

        connection.setAutoCommit(false);

        try(final PreparedStatement preparedDerivative = connection.prepareStatement(insertDerivative);
            final PreparedStatement preparedPair = connection.prepareStatement(insertPair)){
            for(CurrencyPair derivative : derivatives) {
                preparedDerivative.setString(1, derivative.getId());
                preparedDerivative.setString(2, derivative.getTicker());
                preparedDerivative.setString(3, String.valueOf(derivative.getType()));
                preparedDerivative.setString(4, String.valueOf(derivative.getExchange()));
                preparedDerivative.setDouble(5, derivative.getPrice());
                preparedDerivative.addBatch();
                preparedDerivative.executeBatch();

                preparedPair.setString(1, derivative.getId());
                preparedPair.setString(2, derivative.getBaseCurrency());
                preparedPair.setString(3, derivative.getQuoteCurrency());
                preparedPair.setString(4, derivative.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                preparedPair.addBatch();
                preparedPair.executeBatch();
            }

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        }
        connection.setAutoCommit(true);
    }

    @Override
    @SneakyThrows
    public boolean update(CurrencyPair derivative) {
        String updateDerivative = """
               UPDATE public.derivative
               SET ticker=?, instrument=?::derivativetype, exch=?::exchange, price=?
               WHERE derivative.id = ?""";

        String updatePair = """
                UPDATE public.currencypair
                SET basecurrency=?, quotecurrency=?, date=?::timestamp
                WHERE currencypair.id = ?;""";

        connection.setAutoCommit(false);

        try(final PreparedStatement preparedDerivative = connection.prepareStatement(updateDerivative);
            final PreparedStatement preparedPair = connection.prepareStatement(updatePair)){
            preparedDerivative.setString(1, derivative.getTicker());
            preparedDerivative.setString(2, String.valueOf(derivative.getType()));
            preparedDerivative.setString(3, String.valueOf(derivative.getExchange()));
            preparedDerivative.setDouble(4, derivative.getPrice());
            preparedDerivative.setString(5, derivative.getId());
            preparedDerivative.addBatch();
            preparedDerivative.executeBatch();

            preparedPair.setString(1, derivative.getBaseCurrency());
            preparedPair.setString(2, derivative.getQuoteCurrency());
            preparedPair.setString(3, derivative.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            preparedPair.setString(4, derivative.getId());
            preparedPair.addBatch();
            preparedPair.executeBatch();

            connection.commit();
            connection.setAutoCommit(true);

            return true;
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(String id) {
        String deletePair = """
                DELETE FROM public.currencypair
                WHERE id = ?;""";

        String deleteDerivative = """
                DELETE FROM public.derivative
                WHERE id = ?;""";

        try(final PreparedStatement preparedDerivative = connection.prepareStatement(deleteDerivative);
            final PreparedStatement preparedPair = connection.prepareStatement(deletePair)){
            preparedPair.setString(1, id);
            preparedDerivative.setString(1, id);

            return (preparedPair.executeUpdate() > 0) && (preparedDerivative.executeUpdate() > 0);
        }
    }

    @Override
    @SneakyThrows
    public CurrencyPair findById(String id) {
        String findPairById = """
            SELECT * FROM derivative
            RIGHT OUTER JOIN currencypair ON derivative.id = currencypair.id
            WHERE currencypair.id = ?;""";

        try(PreparedStatement preparedPair = connection.prepareStatement(findPairById)){
            preparedPair.setString(1, id);
            ResultSet resultSet = preparedPair.executeQuery();
            while(resultSet.next()){
                String ticker = resultSet.getString("ticker");
                DerivativeType type = DerivativeType.valueOf(resultSet.getString("instrument"));
                Exchange exchange = Exchange.valueOf(resultSet.getString("exch"));
                double price = resultSet.getDouble("price");
                String baseCurrency = resultSet.getString("basecurrency");
                String quoteCurrency = resultSet.getString("quotecurrency");
                LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getString("date"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                CurrencyPair pair =  new CurrencyPair(ticker, type, exchange, price, baseCurrency, quoteCurrency, localDateTime);
                pair.setId(id);
                return pair;
            }
        }
        return null;
    }

    @Override
    @SneakyThrows
    public CurrencyPair findByTicker(String ticker) {
        String findStockByTicker = """
            SELECT * FROM derivative
            RIGHT OUTER JOIN currencypair ON derivative.id = currencypair.id
            WHERE derivative.ticker = ?;""";

        try(PreparedStatement preparedStock = connection.prepareStatement(findStockByTicker)){
            preparedStock.setString(1, ticker);
            ResultSet resultSet = preparedStock.executeQuery();
            while(resultSet.next()){
                String id = resultSet.getString("id");
                DerivativeType type = DerivativeType.valueOf(resultSet.getString("instrument"));
                Exchange exchange = Exchange.valueOf(resultSet.getString("exch"));
                double price = resultSet.getDouble("price");
                String baseCurrency = resultSet.getString("basecurrency");
                String quoteCurrency = resultSet.getString("quotecurrency");
                LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getString("date"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                CurrencyPair pair =  new CurrencyPair(ticker, type, exchange, price, baseCurrency, quoteCurrency, localDateTime);
                pair.setId(id);
                return pair;
            }
        }
        return null;
    }

    @Override
    @SneakyThrows
    public List<CurrencyPair> getAll() {
        String getAllPair = """
                SELECT * FROM derivative
                RIGHT OUTER JOIN currencypair ON derivative.id = currencypair.id;""";

        List<CurrencyPair> pairList = new ArrayList<>();

        try (PreparedStatement preparedPair = connection.prepareStatement(getAllPair)) {
            ResultSet resultSet = preparedPair.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String ticker = resultSet.getString("ticker");
                DerivativeType type = DerivativeType.valueOf(resultSet.getString("instrument"));
                Exchange exchange = Exchange.valueOf(resultSet.getString("exch"));
                double price = resultSet.getDouble("price");
                String baseCurrency = resultSet.getString("basecurrency");
                String quoteCurrency = resultSet.getString("quotecurrency");
                LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getString("date"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                CurrencyPair pair = new CurrencyPair(ticker, type, exchange, price, baseCurrency, quoteCurrency, localDateTime);
                pair.setId(id);
                pair.setId(id);
                pairList.add(pair);
            }
            return pairList;
        }
    }
}
