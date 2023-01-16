package org.tg.services.auth;

import org.tg.dao.auth.UserOTPDao;
import org.tg.dto.OtpDto;
import org.tg.exceptions.CustomSQLException;

import java.util.Optional;

public class UserOTPService {
    UserOTPDao userOTPDao = new UserOTPDao();

    public Optional<Long> save(OtpDto otpDto) throws CustomSQLException {
        return userOTPDao.save(otpDto);
    }
}
