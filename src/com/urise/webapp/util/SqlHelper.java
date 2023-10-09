package com.urise.webapp.util;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String query, SqlExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {
            return executor.execute(ps);
        } catch (SQLException e) {
            String sqlState = e.getSQLState();
            if ("23505".equals(sqlState)) {
                throw new ExistStorageException(e.getMessage());
            } else
                throw new StorageException(e);
        }
    }

    public interface SqlExecutor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}
