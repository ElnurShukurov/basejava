package com.urise.webapp.util;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public static <T> T execute(ConnectionFactory connectionFactory, String query, SqlExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface SqlExecutor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}
