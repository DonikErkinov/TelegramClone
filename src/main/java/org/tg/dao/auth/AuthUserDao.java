package org.tg.dao.auth;

import org.tg.config.DbConfigurer;
import org.tg.domains.auth.UserEntity;
import org.tg.dto.auth.UserCreateDTO;
import org.tg.exceptions.CustomSQLException;
import uz.jl.BaseUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AuthUserDao {

    public Optional<String> login(String username, String password) throws CustomSQLException {
        try {
            PreparedStatement pstm = DbConfigurer
                    .getConnection()
                    .prepareStatement("select hr.user_login(?,?);");
            pstm.setString(1, username);
            pstm.setString(2, password);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {

                return Optional.of(resultSet.getString(1));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new CustomSQLException(e.getMessage());
        }
    }

    public Optional<Long> register(UserCreateDTO dto) throws CustomSQLException {
        try {
            CallableStatement cs = DbConfigurer
                    .getConnection()
                    .prepareCall("select hr.user_create(?)");
            cs.setString(1, BaseUtils.gson.toJson(dto));
            ResultSet resultSet = cs.executeQuery();

            if (resultSet.next()) {
                return Optional.of(resultSet.getLong(1));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }
    }

    public UserEntity findUserByPhoneNumber(String phoneNumber) throws CustomSQLException {
        try {
            PreparedStatement ps = DbConfigurer
                    .getConnection()
                    .prepareStatement("select t.* from hr.users t where t.phone  = ?;");
            ps.setString(1, phoneNumber);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return UserEntity.childBuilder()
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .language(resultSet.getString("language"))
                        .phone(resultSet.getString("phone"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .build();
            }
            return null;
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }
    }
}
