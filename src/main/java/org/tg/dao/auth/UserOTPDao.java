package org.tg.dao.auth;

import org.tg.config.DbConfigurer;
import org.tg.dto.OtpDto;
import org.tg.exceptions.CustomSQLException;
import uz.jl.BaseUtils;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserOTPDao {
    public Optional<Long> save(OtpDto otpDto) throws CustomSQLException {
        try {
            CallableStatement cs = DbConfigurer.getConnection()
                    .prepareCall("select hr.user_otp_create(?);");
            cs.setString(1, BaseUtils.gson.toJson(otpDto));
            ResultSet resultSet = cs.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getLong(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }
    }
}
