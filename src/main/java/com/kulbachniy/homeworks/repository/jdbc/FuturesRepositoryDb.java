package com.kulbachniy.homeworks.repository.jdbc;

import com.kulbachniy.homeworks.config.JdbcConfig;
import com.kulbachniy.homeworks.model.derivative.DerivativeType;
import com.kulbachniy.homeworks.model.derivative.Exchange;
import com.kulbachniy.homeworks.model.derivative.Futures;
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

public class FuturesRepositoryDb implements CrudRepository<Futures> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuturesRepositoryDb.class);

    private final Connection connection = JdbcConfig.getConnection();

    private static FuturesRepositoryDb instance;

    private FuturesRepositoryDb() {
        LOGGER.info("Futures Repository have been created");
    }

    public static FuturesRepositoryDb getInstance(){
        if(instance == null) {
            instance = new FuturesRepositoryDb();
        }
        return instance;
    }

    @Override
    @SneakyThrows
    public void save(Futures derivative) {
        String insertDerivative = """
            INSERT INTO public.derivative( id, ticker, instrument, exch, price)
            VALUES (?, ?, ?::derivativetype, ?::exchange, ?);""";

        String insertFutures = """
                INSERT INTO public.futures(
                	id, commodity, expirationdate)
                	VALUES (?, ?, ?::timestamp);""";

        connection.setAutoCommit(false);

        try(final PreparedStatement preparedDerivative = connection.prepareStatement(insertDerivative);
            final PreparedStatement preparedFutures = connection.prepareStatement(insertFutures)){
                preparedDerivative.setString(1, derivative.getId());
                preparedDerivative.setString(2, derivative.getTicker());
                preparedDerivative.setString(3, String.valueOf(derivative.getType()));
                preparedDerivative.setString(4, String.valueOf(derivative.getExchange()));
                preparedDerivative.setDouble(5, derivative.getPrice());
                preparedDerivative.addBatch();
                preparedDerivative.executeBatch();

                preparedFutures.setString(1, derivative.getId());
                preparedFutures.setString(2, derivative.getCommodity());
                preparedFutures.setString(3, derivative.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                preparedFutures.addBatch();
                preparedFutures.executeBatch();

                connection.commit();
                connection.setAutoCommit(true);
        }
    }

    @Override
    @SneakyThrows
    public void saveAll(List<Futures> derivatives) {
        String insertDerivative = """
            INSERT INTO public.derivative( id, ticker, instrument, exch, price)
            VALUES (?, ?, ?::derivativetype, ?::exchange, ?);""";

        String insertFutures = """
                INSERT INTO public.futures(
                	id, commodity, expirationdate)
                	VALUES (?, ?, ?::timestamp);""";

        connection.setAutoCommit(false);

        try(final PreparedStatement preparedDerivative = connection.prepareStatement(insertDerivative);
            final PreparedStatement preparedFutures = connection.prepareStatement(insertFutures)){
            for(Futures derivative : derivatives) {
                preparedDerivative.setString(1, derivative.getId());
                preparedDerivative.setString(2, derivative.getTicker());
                preparedDerivative.setString(3, String.valueOf(derivative.getType()));
                preparedDerivative.setString(4, String.valueOf(derivative.getExchange()));
                preparedDerivative.setDouble(5, derivative.getPrice());
                preparedDerivative.addBatch();
                preparedDerivative.executeBatch();

                preparedFutures.setString(1, derivative.getId());
                preparedFutures.setString(2, derivative.getCommodity());
                preparedFutures.setString(3, derivative.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                preparedFutures.addBatch();
                preparedFutures.executeBatch();
            }

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        }
        connection.setAutoCommit(true);
    }

    @Override
    @SneakyThrows
    public boolean update(Futures derivative) {
        String updateDerivative = """
               UPDATE public.derivative
               SET ticker=?, instrument=?::derivativetype, exch=?::exchange, price=?
               WHERE derivative.id = ?""";

        String updateFutures = """
                UPDATE public.futures
                SET commodity=?, expirationdate=?::timestamp
                WHERE futures.id = ?;""";

        connection.setAutoCommit(false);

        try(final PreparedStatement preparedDerivative = connection.prepareStatement(updateDerivative);
            final PreparedStatement preparedFutures = connection.prepareStatement(updateFutures)){
            preparedDerivative.setString(1, derivative.getTicker());
            preparedDerivative.setString(2, String.valueOf(derivative.getType()));
            preparedDerivative.setString(3, String.valueOf(derivative.getExchange()));
            preparedDerivative.setDouble(4, derivative.getPrice());
            preparedDerivative.setString(5, derivative.getId());
            preparedDerivative.addBatch();
            preparedDerivative.executeBatch();

            preparedFutures.setString(1, derivative.getCommodity());
            preparedFutures.setString(2, derivative.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            preparedFutures.setString(3, derivative.getId());
            preparedFutures.addBatch();
            preparedFutures.executeBatch();

            connection.commit();
            connection.setAutoCommit(true);

            return true;
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(String id) {
        String deleteFutures = """
                DELETE FROM public.futures
                WHERE id = ?;""";

        String deleteDerivative = """
                DELETE FROM public.derivative
                WHERE id = ?;""";

        try(final PreparedStatement preparedDerivative = connection.prepareStatement(deleteDerivative);
            final PreparedStatement preparedFutures = connection.prepareStatement(deleteFutures)){
            preparedFutures.setString(1, id);
            preparedDerivative.setString(1, id);

            return (preparedFutures.executeUpdate() > 0) && (preparedDerivative.executeUpdate() > 0);
        }
    }

    @Override
    @SneakyThrows
    public Futures findById(String id) {
        String findFuturesById = """
            SELECT * FROM derivative
            RIGHT OUTER JOIN futures ON derivative.id = futures.id
            WHERE futures.id = ?;""";

        try(PreparedStatement preparedFutures = connection.prepareStatement(findFuturesById)){
            preparedFutures.setString(1, id);
            ResultSet resultSet = preparedFutures.executeQuery();
            while(resultSet.next()){
                String ticker = resultSet.getString("ticker");
                DerivativeType type = DerivativeType.valueOf(resultSet.getString("instrument"));
                Exchange exchange = Exchange.valueOf(resultSet.getString("exch"));
                double price = resultSet.getDouble("price");
                String commodity = resultSet.getString("commodity");
                LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getString("expirationdate"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                Futures futures =  new Futures(ticker, type, exchange, price, commodity, localDateTime);
                futures.setId(id);
                return futures;
            }
        }
        return null;
    }

    @Override
    @SneakyThrows
    public Futures findByTicker(String ticker) {
        String findFuturesByTicker = """
            SELECT * FROM derivative
            RIGHT OUTER JOIN futures ON derivative.id = futures.id
            WHERE derivative.ticker = ?;""";

        try(PreparedStatement preparedFutures = connection.prepareStatement(findFuturesByTicker)){
            preparedFutures.setString(1, ticker);
            ResultSet resultSet = preparedFutures.executeQuery();
            while(resultSet.next()){
                String id = resultSet.getString("id");
                DerivativeType type = DerivativeType.valueOf(resultSet.getString("instrument"));
                Exchange exchange = Exchange.valueOf(resultSet.getString("exch"));
                double price = resultSet.getDouble("price");
                String commodity = resultSet.getString("commodity");
                LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getString("expirationdate"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                Futures futures =  new Futures(ticker, type, exchange, price, commodity, localDateTime);
                futures.setId(id);
                return futures;
            }
        }
        return null;
    }

    @Override
    @SneakyThrows
    public List<Futures> getAll() {
        String getAllFutures = """
            SELECT * FROM derivative
            RIGHT OUTER JOIN futures ON derivative.id = futures.id;""";

        List<Futures> futuresList = new ArrayList<>();

        try(PreparedStatement preparedFutures = connection.prepareStatement(getAllFutures)){
            ResultSet resultSet = preparedFutures.executeQuery();
            while(resultSet.next()){
                String id = resultSet.getString("id");
                String ticker = resultSet.getString("ticker");
                DerivativeType type = DerivativeType.valueOf(resultSet.getString("instrument"));
                Exchange exchange = Exchange.valueOf(resultSet.getString("exch"));
                double price = resultSet.getDouble("price");
                String commodity = resultSet.getString("commodity");
                LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getString("expirationdate"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                Futures futures =  new Futures(ticker, type, exchange, price, commodity, localDateTime);
                futures.setId(id);
                futuresList.add(futures);
            }
            return futuresList;
        }
    }
}
