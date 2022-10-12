package com.kulbachniy.homeworks.repository.jdbc;

import com.kulbachniy.homeworks.config.JdbcConfig;
import com.kulbachniy.homeworks.model.Portfolio;
import com.kulbachniy.homeworks.model.derivative.Derivative;
import com.kulbachniy.homeworks.repository.PortfolioRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PortfolioRepositoryDb implements PortfolioRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioRepositoryDb.class);

    private final Connection connection = JdbcConfig.getConnection();

    private static PortfolioRepositoryDb instance;

    private PortfolioRepositoryDb() {
        LOGGER.info("Trader Repository have been created");
    }

    public static PortfolioRepositoryDb getInstance(){
        if(instance == null) {
            instance = new PortfolioRepositoryDb();
        }
        return instance;
    }

    @Override
    @SneakyThrows
    public void save(Portfolio portfolio) {
        String insertPortfolio = """
                INSERT INTO public.portfolio( id, sum, created)
                VALUES (?, ?, ?::timestamp);""";

        String insertRelations = """
                INSERT INTO public.derivative_portfolio(id_p, id_d)
                VALUES (?, ?);""";

        connection.setAutoCommit(false);

        try(final PreparedStatement preparedPortfolio = connection.prepareStatement(insertPortfolio);
            final PreparedStatement preparedRelations = connection.prepareStatement(insertRelations)){
            preparedPortfolio.setString(1, portfolio.getId());
            preparedPortfolio.setDouble(2, portfolio.getSum());
            preparedPortfolio.setString(3, portfolio.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            preparedPortfolio.addBatch();
            preparedPortfolio.executeBatch();

            for(Derivative d : portfolio.getDerivativeList()){
                preparedRelations.setString(1, portfolio.getId());
                preparedRelations.setString(2, d.getId());
                preparedRelations.addBatch();
            }
            preparedRelations.executeBatch();

            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    @Override
    @SneakyThrows
    public void saveAll(List<Portfolio> portfolios) {
        String insertPortfolio = """
                INSERT INTO public.portfolio( id, sum, created)
                VALUES (?, ?, ?::timestamp);""";

        String insertRelations = """
                INSERT INTO public.derivative_portfolio(id_p, id_d)
                VALUES (?, ?);""";

        connection.setAutoCommit(false);

        try(final PreparedStatement preparedPortfolio = connection.prepareStatement(insertPortfolio);
            final PreparedStatement preparedRelations = connection.prepareStatement(insertRelations)){
            for(Portfolio portfolio : portfolios) {
                    preparedPortfolio.setString(1, portfolio.getId());
                    preparedPortfolio.setDouble(2, portfolio.getSum());
                    preparedPortfolio.setString(3, portfolio.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    preparedPortfolio.addBatch();
                    preparedPortfolio.executeBatch();

                    for(Derivative d : portfolio.getDerivativeList()){
                        preparedRelations.setString(1, portfolio.getId());
                        preparedRelations.setString(2, d.getId());
                        preparedRelations.addBatch();
                    }
                    preparedRelations.executeBatch();
            }

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        }
        connection.setAutoCommit(true);
    }

    @Override
    @SneakyThrows
    public boolean update(Portfolio portfolio) {
       if(delete(portfolio.getId())){
           save(portfolio);
           return true;
       }
       return false;
    }

    @Override
    @SneakyThrows
    public boolean delete(String id) {
        String deletePortfolio = """
                DELETE FROM public.portfolio
                WHERE id = ?;""";

        try(final PreparedStatement preparedPortfolio = connection.prepareStatement(deletePortfolio)){
            preparedPortfolio.setString(1, id);
            return (preparedPortfolio.executeUpdate() > 0);
        }
    }

    @Override
    @SneakyThrows
    public Portfolio findById(String id) {
        String findPortfolioById = """
            SELECT * FROM public.portfolio
            WHERE portfolio.id = ?;""";

        Portfolio portfolio = new Portfolio();
        portfolio.setId(id);

        try(PreparedStatement preparedPortfolio = connection.prepareStatement(findPortfolioById)){
            preparedPortfolio.setString(1, id);
            ResultSet resultSet = preparedPortfolio.executeQuery();
            while(resultSet.next()){
                LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getString("created"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                String findDerivativeId = """
                        SELECT * FROM public.derivative_portfolio
                        WHERE derivative_portfolio.id_p = ?;""";

                try(PreparedStatement preparedDerivative = connection.prepareStatement(findDerivativeId)){
                    preparedDerivative.setString(1, id);
                    ResultSet resultSetDerivatives = preparedDerivative.executeQuery();
                    while(resultSetDerivatives.next()){
                        String idDerivative = resultSetDerivatives.getString("id_d");

                        Set<Derivative> derivativeSet = new HashSet<>();
                        derivativeSet.add(CurrencyPairRepositoryDb.getInstance().findById(idDerivative));
                        derivativeSet.add(FuturesRepositoryDb.getInstance().findById(idDerivative));
                        derivativeSet.add(StockRepositoryDb.getInstance().findById(idDerivative));

                       derivativeSet.stream().filter(Objects::nonNull).forEach(portfolio::addDerivative);
                    }
                }
                portfolio.setTime(localDateTime);
                return portfolio;
            }
        }
        return null;
    }

    @Override
    @SneakyThrows
    public List<Portfolio> getAll() {
        List<Portfolio> portfolios = new ArrayList<>();

        String findPortfolioById = """
            SELECT * FROM public.portfolio;""";

        try(PreparedStatement preparedPortfolio = connection.prepareStatement(findPortfolioById)){
            ResultSet resultSet = preparedPortfolio.executeQuery();
            while(resultSet.next()){
                String id = resultSet.getString("id");

                portfolios.add(findById(id));
            }
            return portfolios;
        }
    }

    @SneakyThrows
    public List<Portfolio> getExpensiveThan(double sum){
        String expensiveThan = """
                select * from portfolio where sum >?;""";

        List<Portfolio> portfolios = new ArrayList<>();
        try(PreparedStatement preparePortfolio = connection.prepareStatement(expensiveThan)){
            preparePortfolio.setDouble(1, sum);
            ResultSet resultSet = preparePortfolio.executeQuery();
            while(resultSet.next()){
                String id = resultSet.getString("id");

                portfolios.add(findById(id));
            }
            return portfolios;
        }
    }

    @SneakyThrows
    public int quantity() {
        String quantity = """
                SELECT COUNT(*) AS count FROM portfolio;""";

        try (PreparedStatement preparePortfolio = connection.prepareStatement(quantity)) {
            ResultSet resultSet = preparePortfolio.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("count");
            }
            return 0;
        }
    }

    @SneakyThrows
    public void updateTime(Derivative derivative){
        String updateTime = """
                UPDATE portfolio
                SET created = ?::timestamp
                WHERE portfolio.id IN (select id_p FROM derivative_portfolio WHERE id_d = ?);""";

        try (PreparedStatement preparePortfolio = connection.prepareStatement(updateTime)) {
            preparePortfolio.setString(1, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            preparePortfolio.setString(2, derivative.getId());
            preparePortfolio.executeUpdate();
        }
    }

    @SneakyThrows
    public Map<Double, List<Portfolio>> groupBySum(){
        String updateTime = """
                SELECT sum, COUNT(*),STRING_AGG(portfolio.id, ', ')
                FROM portfolio
                GROUP BY sum""";

        Map<Double, List<Portfolio>> listMap = new HashMap<>();
        try(PreparedStatement preparedPortfolio = connection.prepareStatement(updateTime)){
            ResultSet resultSetDerivatives = preparedPortfolio.executeQuery();
            while(resultSetDerivatives.next()){
                Double sum = resultSetDerivatives.getDouble("sum");
                String stringAgg = resultSetDerivatives.getString("string_agg");

                String[] strings = stringAgg.split(", ");
                List<Portfolio> portfolios = new ArrayList<>();
                for(String s : strings){
                    portfolios.add(findById(s));
                }

                listMap.put(sum, portfolios);
            }
            return listMap;
        }
    }
}
